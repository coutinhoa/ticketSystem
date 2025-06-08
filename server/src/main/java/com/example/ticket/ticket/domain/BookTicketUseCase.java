package com.example.ticket.ticket.domain;

import com.example.ticket.event.Event;
import com.example.ticket.event.EventEntity;

import java.util.List;

public interface BookTicketUseCase {
    List<Ticket> bookTickets(List<Ticket> ticket, EventEntity event);
}
