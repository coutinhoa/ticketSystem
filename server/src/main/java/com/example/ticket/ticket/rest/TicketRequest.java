package com.example.ticket.ticket.rest;

import com.example.ticket.ticket.domain.TicketStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class TicketRequest {
    private String type;
    private double price;
    private int quantity;
    private Attendee attendee;
    private TicketStatus status;

}
