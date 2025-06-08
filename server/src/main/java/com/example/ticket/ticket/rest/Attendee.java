package com.example.ticket.ticket.rest;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Attendee {
    private String firstName;
    private String lastName;
    private String attendeeEmail;
}
