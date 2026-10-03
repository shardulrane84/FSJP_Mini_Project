package com.example.demo.service;

import com.example.demo.entity.Event;
import com.example.demo.repository.EventRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventService {
    private final EventRepository repo;

    public EventService(EventRepository repo) {
        this.repo = repo;
    }

    public Event create(Event event) {
        return repo.save(event);
    }

    public Event findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
    }

    public List<Event> findAll() {
        return repo.findAll();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public void cancel(Long id) {
        Event e = repo.findById(id).orElseThrow();
        e.setStatus(Event.Status.CANCELLED);
        repo.save(e);
    }
}