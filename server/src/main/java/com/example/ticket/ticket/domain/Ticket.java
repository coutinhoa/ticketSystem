package com.example.ticket.ticket.domain;

import com.example.ticket.ticket.rest.Attendee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Ticket {
    private Long id;
    private Long userId;
    private Long eventId;
    private String type;
    private double price;
    private Attendee attendee;
    private TicketStatus status;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
