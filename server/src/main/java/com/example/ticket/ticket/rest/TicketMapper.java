package com.example.ticket.ticket.rest;

import com.example.ticket.ticket.domain.Ticket;
import com.example.ticket.ticket.persistence.TicketEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TicketMapper {

    public List<Ticket> mapToTicket(List<TicketRequest> ticket, Long eventId, Long userId) {
        return ticket.stream()
                .map(t -> Ticket.builder()
                        .eventId(eventId)
                        .userId(userId)
                        .type(t.getType())
                        .price(t.getPrice())
                        .attendee(Attendee.builder()
                                .firstName(t.getAttendee().getFirstName())
                                .lastName(t.getAttendee().getLastName())
                                .attendeeEmail(t.getAttendee().getAttendeeEmail())
                                .build())
                        .status(t.getStatus())
                        .build())
                .toList();
    }

    public List<TicketResponse> toResponse(List<Ticket> ticket, Long eventId) {

        return ticket.stream()
                .map(t -> TicketResponse.builder()
                        .id(t.getId())
                        .eventId(eventId)
                        .attendeeFirstname(t.getAttendee().getFirstName())
                        .attendeeSurname(t.getAttendee().getLastName())
                        .attendeeEmail(t.getAttendee().getAttendeeEmail())
                        .status(t.getStatus())
                        .build())
                .toList();
    }

    public Ticket toDomain(TicketEntity ticketEntity) {
        return Ticket.builder()
                .id(ticketEntity.getId())
                .userId(ticketEntity.getUserId())
                .eventId(ticketEntity.getEvent().getId())
                .attendee(Attendee.builder()
                        .firstName(ticketEntity.getFirstName() == null ? null : ticketEntity.getFirstName())
                        .lastName(ticketEntity.getLastName() == null ? null : ticketEntity.getLastName())
                        .attendeeEmail(ticketEntity.getEmail() == null ? null : ticketEntity.getEmail())
                        .build())
                .status(ticketEntity.getStatus())
                .createdAt(ticketEntity.getCreatedAt() == null ? null : ticketEntity.getCreatedAt())
                .build();
    }
}
