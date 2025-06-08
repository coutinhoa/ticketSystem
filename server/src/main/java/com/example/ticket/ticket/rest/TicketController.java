package com.example.ticket.ticket.rest;

import com.example.ticket.event.Event;
import com.example.ticket.event.EventEntity;
import com.example.ticket.event.EventService;
import com.example.ticket.ticket.domain.BookTicketUseCase;
import com.example.ticket.ticket.domain.Ticket;
import com.example.ticket.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/event")
public class TicketController {

    private final TicketMapper ticketMapper;
    private final BookTicketUseCase bookTicketUseCase;
    private final UserService userService;
    private EventService eventService;

    @PostMapping(value = "/{eventId}/tickets",  produces = "application/json")
    @ResponseBody
        public ResponseEntity<List<TicketResponse>>  bookTickets(@RequestBody List<TicketRequest> ticketRequest,
                                                          @PathVariable Long eventId) {

        User user = userService.getCurrentUser();

        if(user == null) {
            return ResponseEntity.badRequest().build();
        }

        EventEntity event = eventService.getEventEntityById(eventId);
        int availableTickets = eventService.getNumberOfAvailableTickets(event.getAvailableTickets());

        if(availableTickets < ticketRequest.size()) {
            throw new IllegalArgumentException("Not enough tickets available");
        }

        List<Ticket> tickets= ticketMapper.mapToTicket(ticketRequest, eventId, user.getId());
        List<Ticket> bookedTickets = bookTicketUseCase.bookTickets(tickets, event);
        eventService.reduceTickets(eventId, bookedTickets.size());
        return  ResponseEntity.ok(ticketMapper.toResponse(bookedTickets, eventId));
    }
}
