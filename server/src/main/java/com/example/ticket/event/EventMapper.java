package com.example.ticket.event;

import com.example.ticket.EventRequest;
import com.example.ticket.event.elastic.EventElastic;
import com.example.ticket.ticket.domain.Ticket;
import com.example.ticket.ticket.persistence.TicketEntity;
import com.example.ticket.ticket.rest.Attendee;
import com.example.ticket.ticket.rest.TicketMapper;
import com.example.ticket.ticket.rest.TicketRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class EventMapper {

    public Event toEvent(EventEntity event) {
        return Event.builder()
                .id(event.getId())
                .date(LocalDate.from(event.getDate()))
                .location(event.getLocation())
                .artist(event.getArtist())
                .availableTickets(event.getAvailableTickets())
                .build();
    }

    public List<Event> toEvents(List<EventEntity> eventEntities) {
        return eventEntities.stream()
                .map(this::toEvent)
                .toList();
    }

    public List<EventResponse> toResponse(List<Event> events) {
        return events.stream()
                .map(event -> EventResponse.builder()
                        .eventId(event.getId())
                        .eventDate(event.getDate())
                        .eventLocation(event.getLocation())
                        .artist(event.getArtist())
                        .availableTickets(event.getAvailableTickets())
                        .build())
                .toList();
    }

    public EventResponse toResponse(Event event) {
        return EventResponse.builder()
                        .eventId(event.getId())
                        .eventDate(event.getDate())
                        .eventLocation(event.getLocation())
                        .artist(event.getArtist())
                        .availableTickets(event.getAvailableTickets())
                        .build();
    }

    public EventEntity toEventEntity(Event event) {
        return EventEntity.builder()
                .id(event.getId())
                .date(event.getDate())
                .location(event.getLocation())
                .artist(event.getArtist())
                .availableTickets(event.getAvailableTickets())
                .tickets(event.getTickets() == null ? null : event.getTickets().stream()
                        .map(this::toTicketEntity)
                        .toList())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public EventElastic toEventElastic(EventEntity eventEntity) {
        return EventElastic.builder()
                .id(String.valueOf(eventEntity.getId()))
                .location(eventEntity.getLocation())
                .date(eventEntity.getDate())
                .artist(eventEntity.getArtist())
                .availableTickets(eventEntity.getAvailableTickets())
                .createdAt(LocalDateTime.now().toLocalDate())
                .build();
    }


    public Event toEvent(EventRequest event) {
        return Event.builder()
                .location(event.getLocation())
                .date(LocalDate.from(event.getDate()))
                .artist(event.getArtist())
                .availableTickets(event.getAvailableTickets())
                .type(event.getType())
                .createdAt(LocalDate.now())
                .tickets(this.toDomain(event.getTickets()))
                .build();
    }


    public List<Ticket> toDomain(List<TicketRequest> ticket) {
        return ticket.stream()
                .map(t -> Ticket.builder()
                        .attendee(Attendee.builder()
                                .firstName(t.getAttendee() == null ? null : t.getAttendee().getFirstName())
                                .lastName(t.getAttendee() == null ? null : t.getAttendee().getLastName())
                                .attendeeEmail(t.getAttendee() == null ? null : t.getAttendee().getAttendeeEmail())
                                .build())
                        .status(t.getStatus())
                        .build())
                .toList();
    }

    public TicketEntity toTicketEntity(Ticket ticketEntity) {
        return TicketEntity.builder()
                .id(ticketEntity.getId())
                .userId(ticketEntity.getUserId())
                .event(EventEntity.builder().id(ticketEntity.getEventId()).build())
                .firstName(ticketEntity.getAttendee() == null ? null : ticketEntity.getAttendee().getFirstName())
                .lastName(ticketEntity.getAttendee() == null ? null : ticketEntity.getAttendee().getLastName())
                .email(ticketEntity.getAttendee() == null ? null : ticketEntity.getAttendee().getAttendeeEmail())
                .status(ticketEntity.getStatus())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public List<Event> toEventsFromElastic(List<EventElastic> eventElastics) {
    return eventElastics.stream()
                .map(eventElastic -> Event.builder()
                        .id(Long.parseLong(eventElastic.getId()))
                        .date(eventElastic.getDate())
                        .location(eventElastic.getLocation())
                        .artist(eventElastic.getArtist())
                        .availableTickets(eventElastic.getAvailableTickets())
                        .createdAt(eventElastic.getCreatedAt())
                        .build())
                .toList();
    }
}
