package com.example.ticket.event;

import com.example.ticket.EventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/event")
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping(produces = "application/json")
    @ResponseBody
        public ResponseEntity<List<EventResponse>>  getEvents() {
        List<Event> events = eventService.getAllEvents();
        return  ResponseEntity.ok(eventMapper.toResponse(events));
    }

    @GetMapping(value = "/{id}" , produces = "application/json")
    @ResponseBody
    public ResponseEntity<EventResponse>  getEvent(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        return  ResponseEntity.ok(eventMapper.toResponse(event));
    }


    @GetMapping(value = "/search", produces = "application/json")
    @ResponseBody
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
}
