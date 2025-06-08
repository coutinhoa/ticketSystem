package com.example.ticket.ticket.domain;

import com.example.ticket.event.EventEntity;
import com.example.ticket.ticket.persistence.TicketEntity;
import com.example.ticket.ticket.persistence.TicketRepository;
import com.example.ticket.ticket.rest.Attendee;
import com.example.ticket.ticket.rest.TicketMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookTicketUseCaseImplTest {

    @InjectMocks
    public BookTicketUseCaseImpl bookTicketUseCase;

    @Mock
    public TicketRepository ticketRepository;

    @Mock
    public TicketMapper ticketMapper;

    @Test
    void bookTickets_shouldTestBookingTickets_whenEventAndTicketsAreReceived() {
        Ticket ticket = Ticket.builder()
                .id(1L)
                .userId(100L)
                .eventId(1L)
                .type("GENERAL")
                .price(50.0)
                .attendee(Attendee.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .attendeeEmail("email.email.com")
                        .build())
                .status(TicketStatus.BOOKED)
                .build();

        TicketEntity ticketEntity = TicketEntity.builder()
                .id(1L)
                .userId(100L)
                .status(TicketStatus.BOOKED)
                .type("GENERAL")
                .price(50.0)
                .firstName("John")
                .lastName("Doe")
                .email("email@email.com")
                .createdAt(LocalDateTime.parse("2023-09-01T10:00:00"))
                .build();

        EventEntity event = EventEntity.builder()
                .id(1L)
                .location("Stadium")
                .date(LocalDate.of(2023, 10, 1))
                .artist("The Band")
                .availableTickets(100)
                .createdAt(LocalDateTime.parse("2023-09-01T10:00:00"))
                .tickets(List.of(ticketEntity))
                .build();

        when(ticketRepository.saveAll(anyList()))
                .thenReturn(List.of(ticketEntity));

        when(ticketMapper.toDomain(any(TicketEntity.class)))
                .thenReturn(ticket);

        var result = bookTicketUseCase.bookTickets(List.of(ticket),event);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(ticket.getId(), result.get(0).getId());
        assertEquals(ticket.getUserId(), result.get(0).getUserId());
        assertEquals(ticket.getAttendee().getFirstName(), result.get(0).getAttendee().getFirstName());
    }
}