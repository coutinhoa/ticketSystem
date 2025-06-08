package com.example.ticket.event;

import com.example.ticket.ticket.domain.Ticket;
import com.example.ticket.ticket.persistence.TicketEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@Table(name = "T_EVENT")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity {

    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "artist", nullable = false)
    private String artist;

    @Column(name = "available_tickets", nullable = false)
    private int availableTickets;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TicketEntity> tickets;
}
