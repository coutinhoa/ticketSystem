package com.example.ticket.event;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventMapper eventMapper;

    @Test
    void searchEventsPageable_ShouldReturnMappedPage() {
        String artist = "Test Artist";
        String location = "Test Location";
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        EventEntity eventEntity = new EventEntity();
        Event event = Event.builder().build();
        Page<EventEntity> eventEntityPage = new PageImpl<>(List.of(eventEntity), pageable, 1);
        Page<Event> expectedPage = new PageImpl<>(List.of(event), pageable, 1);
        when(eventRepository.findByArtistOrLocation(artist, location, pageable)).thenReturn(eventEntityPage);
        when(eventMapper.toEvent(eventEntity)).thenReturn(event);

        Page<Event> result = eventService.searchEventsPageable(artist, location, page, size);

        assertEquals(expectedPage.getContent(), result.getContent());
        assertEquals(expectedPage.getTotalElements(), result.getTotalElements());
        assertEquals(expectedPage.getPageable(), result.getPageable());
        verify(eventRepository, times(1)).findByArtistOrLocation(artist, location, pageable);
        verify(eventMapper, times(1)).toEvent(eventEntity);
    }

    @Test
    void searchEventsPageable_ShouldReturnEmptyPage_WhenNoResults() {
        String artist = "Nonexistent Artist";
        String location = "Nonexistent Location";
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<EventEntity> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(eventRepository.findByArtistOrLocation(artist, location, pageable)).thenReturn(emptyPage);

        Page<Event> result = eventService.searchEventsPageable(artist, location, page, size);

        assertEquals(0, result.getTotalElements());
        assertEquals(Collections.emptyList(), result.getContent());
        verify(eventRepository, times(1)).findByArtistOrLocation(artist, location, pageable);
        verifyNoInteractions(eventMapper);
    }
}