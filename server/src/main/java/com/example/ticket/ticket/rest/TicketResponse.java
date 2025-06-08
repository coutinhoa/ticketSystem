package com.example.ticket.ticket.rest;

import com.example.ticket.event.EventResponse;
import com.example.ticket.ticket.domain.TicketStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
public class TicketResponse {
    private Long id;
    private Long eventId;
    private String attendeeFirstname;
    private String attendeeSurname;
    private String attendeeEmail;
    private TicketStatus status;
}
