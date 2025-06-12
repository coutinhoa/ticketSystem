package com.example.ticket.event;

import com.example.ticket.event.elastic.EventElastic;
import com.example.ticket.event.elastic.EventElasticRepository;
import com.example.ticket.event.elastic.EventElasticRepositoryImpl;
import com.example.ticket.ticket.domain.BookTicketUseCase;
import com.example.ticket.ticket.domain.Ticket;
import com.example.ticket.ticket.domain.TicketStatus;
import com.example.ticket.ticket.persistence.TicketEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventElasticRepository eventElasticRepository;
    private final EventMapper eventMapper;
    private final BookTicketUseCase bookTicketUseCase;
    private final EventElasticRepositoryImpl eventElasticRepositoryImpl;

    public int getNumberOfAvailableTickets(int eventId) {
        return eventRepository.findAvailableTicketsById(eventId);
    }

    public void reduceTickets(Long eventId, int numberOfTickets) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));
        event.setAvailableTickets(event.getAvailableTickets() - numberOfTickets);
        eventRepository.save(event);
    }

    public Event getEventById(Long eventId) {
        EventEntity eventEntity = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        return eventMapper.toEvent(eventEntity);
    }

    public EventEntity getEventEntityById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));
    }

    public List<Event> getAllEvents() {
        List<EventEntity> eventEntities = eventRepository.findAll();
        return eventMapper.toEvents(eventEntities);
    }

    public List<Event> searchEvents(String artist, String location) {
        /*List<EventEntity> eventEntities = eventRepository.findByArtistOrLocation(artist, location);
        return eventMapper.toEvents(eventEntities);*/

        List<EventElastic> eventElastics = eventElasticRepositoryImpl.findByArtistOrLocation(artist, location);
        if (eventElastics.isEmpty()) {
            throw new EntityNotFoundException("No events found for the given search criteria.");
        }
        return eventMapper.toEventsFromElastic(eventElastics);
    }

    @Transactional
    public Event createEvent(Event event) {
        EventEntity eventEntity = eventMapper.toEventEntity(event);
        eventEntity.setTickets(new ArrayList<>());
        EventEntity savedEntity = eventRepository.save(eventEntity);
        System.out.println("Event saved with ID: " + savedEntity.getId());

        List<Ticket> ticketsToBook = event.getTickets();
        if (ticketsToBook != null) {
            for (Ticket ticket : ticketsToBook) {
                ticket.setEventId(event.getId());
            }
            bookTicketUseCase.bookTickets(ticketsToBook, savedEntity);
        }

        System.out.println("Created and saved tickets for event ID: " + savedEntity.getId());

        Event savedEvent = eventMapper.toEvent(savedEntity);
        EventElastic eventElastic = eventMapper.toEventElastic(savedEntity);
        eventElasticRepository.save(eventElastic);
        System.out.println("Event saved to Elasticsearch with ID: " + eventElastic.getId());
        return savedEvent;
    }

    public Page<Event> searchEventsPageable(String artist, String location, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return eventRepository.findByArtistOrLocation(artist, location, pageable)
                .map(eventMapper::toEvent);
    }

    public void deleteEvent(Long eventId) {
        EventEntity eventEntity = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));
        List<TicketEntity> tickets = eventEntity.getTickets();
        if (tickets != null) {
            for (TicketEntity ticket : tickets) {
                ticket.setStatus(TicketStatus.CANCELLED);
            }
        }
        eventRepository.delete(eventEntity);

        eventElasticRepository.deleteById(String.valueOf(eventId));
    }

    public void handleEventCreated(EventCreated event) {
    }
}
