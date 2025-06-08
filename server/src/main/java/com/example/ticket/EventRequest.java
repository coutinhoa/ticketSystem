package com.example.ticket;

import com.example.ticket.ticket.rest.TicketRequest;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class EventRequest {
    private String location;
    private LocalDateTime date;
    private String artist;
    private int availableTickets;
    private String type;
    private List<TicketRequest> tickets;
}
