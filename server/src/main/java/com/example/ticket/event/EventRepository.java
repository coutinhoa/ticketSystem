package com.example.ticket.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    @Query("SELECT e.availableTickets FROM EventEntity e WHERE e.id = :eventId")
    int findAvailableTicketsById(@Param("eventId") int eventId);


    @Query("""
            SELECT event FROM EventEntity event WHERE
            LOWER(event.artist) LIKE LOWER(CONCAT('%', :artist, '%')) OR
            LOWER(event.location) LIKE LOWER(CONCAT('%', :location, '%'))
            """)
    List<EventEntity> findByArtistOrLocation(@Param("artist") String artist,
                                             @Param("location") String location);


    @Query("""
            SELECT event FROM EventEntity event WHERE
            LOWER(event.artist) LIKE LOWER(CONCAT('%', :artist, '%')) OR
            LOWER(event.location) LIKE LOWER(CONCAT('%', :location, '%'))
            """)
    Page<EventEntity> findByArtistOrLocation(@Param("artist") String artist,
                                             @Param("location") String location,
                                             Pageable pageable);
}
