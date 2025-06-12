package com.example.ticket.event;

import com.example.ticket.EventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/event")
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
        public ResponseEntity<List<EventResponse>>  getEvents() {
        List<Event> events = eventService.getAllEvents();
        return  ResponseEntity.ok(eventMapper.toResponse(events));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EventResponse>  getEvent(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        return  ResponseEntity.ok(eventMapper.toResponse(event));
    }


    @GetMapping(value = "/search")
    public ResponseEntity<List<EventResponse>> searchEvents(
            @RequestParam(value = "artist", required = false) String artist,
            @RequestParam(value = "location", required = false) String location){
        List<Event> events = eventService.searchEvents(artist, location);
        return ResponseEntity.ok(eventMapper.toResponse(events));
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody EventRequest request) {
        Event event = eventMapper.toEvent(request);
        return ResponseEntity.ok(eventService.createEvent(event));
    }

    @GetMapping(value = "/search/pageable")
    public ResponseEntity<Page<EventResponse>> searchEventsPageable(
            @RequestParam(value = "artist", required = false) String artist,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<Event> events = eventService.searchEventsPageable(artist, location, page, size);
        Page<EventResponse> eventResponses = events.map(eventMapper::toResponse);
        return ResponseEntity.ok(eventResponses);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
