package com.example.ticket.ticket.domain;

import com.example.ticket.event.Event;
import com.example.ticket.event.EventEntity;
import com.example.ticket.ticket.persistence.TicketEntity;
import com.example.ticket.ticket.persistence.TicketRepository;
import com.example.ticket.ticket.rest.TicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
class BookTicketUseCaseImpl implements BookTicketUseCase {

    public final TicketRepository ticketRepository;
    public final TicketMapper ticketMapper;

    @Override
    @Transactional
    public List<Ticket> bookTickets(List<Ticket> tickets, EventEntity event) {

        List<TicketEntity> ticketEntities = tickets.stream()
                .map(
                    ticket-> TicketEntity.builder()
                            .id(ticket.getId())
                            .userId(ticket.getUserId())
                            .status(TicketStatus.BOOKED)
                            .firstName(ticket.getAttendee().getFirstName())
                            .lastName(ticket.getAttendee().getLastName())
                            .email(ticket.getAttendee().getAttendeeEmail())
                            .createdAt(ticket.getCreatedAt())
                            .type(ticket.getType() == null ? "GENERAL" : ticket.getType())
                            .event(event)
                            .build())
                .toList();

        List<TicketEntity> ticketsEntities = ticketRepository.saveAll(ticketEntities);
        return ticketsEntities.stream()
                .map(ticketMapper::toDomain)
                .toList();
    }
}
