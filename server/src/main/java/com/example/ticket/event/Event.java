package com.example.ticket.event;

import com.example.ticket.ticket.domain.Ticket;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Event {
    private Long id;
    private String location;
    private LocalDate date;
    private String artist;
    private int availableTickets;
    private String type;
    private LocalDate createdAt;
    private List<Ticket> tickets;
}
