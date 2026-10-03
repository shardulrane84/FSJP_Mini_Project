package com.example.demo.repository;

import com.example.demo.entity.Event;
import com.example.demo.entity.EventRegistration;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Long> {
    boolean existsByStudentAndEvent(User student, Event event);

    List<EventRegistration> findByStudent(User student);

    Optional<EventRegistration> findByTicketCode(String ticketCode);

    List<EventRegistration> findByEvent(Event event);
}