package com.example.ticket.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class EventResponse {

    private Long eventId;
    private LocalDate eventDate;
    private String eventLocation;
    private String artist;
    private int availableTickets;
}
