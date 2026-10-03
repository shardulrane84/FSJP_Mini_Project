package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.EventRegistrationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventRegistrationService {

    private final EventRegistrationRepository repo;

    public EventRegistrationService(EventRegistrationRepository repo) {
        this.repo = repo;
    }

    public EventRegistration register(User student, Event event) {
        if (repo.existsByStudentAndEvent(student, event)) {
            throw new IllegalStateException("You are already registered for this event.");
        }
        EventRegistration reg = new EventRegistration();
        reg.setStudent(student);
        reg.setEvent(event);
        return repo.save(reg);
    }

    public List<EventRegistration> findByStudent(User student) {
        return repo.findByStudent(student);
    }

    public EventRegistration findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Registration not found"));
    }

    public List<EventRegistration> findByEvent(Event event) {
        return repo.findByEvent(event);
    }

    public EventRegistration markAttendance(String ticketCode) {
        EventRegistration reg = repo.findByTicketCode(ticketCode)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ticket — no matching registration."));

        Event event = reg.getEvent();
        LocalDateTime windowStart = LocalDateTime.of(event.getEventDate(), event.getStartTime());
        LocalDateTime windowEnd = LocalDateTime.of(event.getEventDate(), event.getEndTime());
        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(windowStart)) {
            throw new IllegalStateException("This event hasn't started yet — attendance can't be marked.");
        }
        if (now.isAfter(windowEnd)) {
            throw new IllegalStateException("This event has already ended — attendance window is closed.");
        }

        // Idempotent: only set attended + timestamp on the FIRST successful scan.
        // Repeat scans within the window just re-confirm, no duplicate write, no error.
        if (!reg.isAttended()) {
            reg.setAttended(true);
            reg.setAttendedAt(now);
            repo.save(reg);
        }
        return reg;
    }
}