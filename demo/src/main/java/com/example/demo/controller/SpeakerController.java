package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EventRegistrationService;
import com.example.demo.service.PropertyApplicationService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/speaker")
public class SpeakerController {

    private final PropertyApplicationService applicationService;
    private final PropertyRepository propertyRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventRegistrationService registrationService;

    public SpeakerController(PropertyApplicationService applicationService,
            PropertyRepository propertyRepository,
            EventRepository eventRepository,
            UserRepository userRepository,
            EventRegistrationService registrationService) {
        this.applicationService = applicationService;
        this.propertyRepository = propertyRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.registrationService = registrationService;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "speaker/dashboard";
    }

    @GetMapping("/properties")
    public String viewProperties(Model model) {
        model.addAttribute("properties", propertyRepository.findAll());
        model.addAttribute("events", eventRepository.findAll());
        return "speaker/properties";
    }

    @PostMapping("/properties/apply")
    public String apply(@RequestParam Long propertyId, @RequestParam Long eventId, Authentication auth) {
        User speaker = userRepository.findByEmail(auth.getName()).orElseThrow();
        PropertyApplication app = new PropertyApplication();
        app.setSpeaker(speaker);
        app.setProperty(propertyRepository.findById(propertyId).orElseThrow());
        app.setEvent(eventRepository.findById(eventId).orElseThrow());
        applicationService.apply(app);
        return "redirect:/speaker/applications";
    }

    @GetMapping("/applications")
    public String myApplications(Model model, Authentication auth) {
        User speaker = userRepository.findByEmail(auth.getName()).orElseThrow();
        model.addAttribute("applications", applicationService.findBySpeaker(speaker));
        return "speaker/applications";
    }

    @GetMapping("/events/{id}/attendance")
    public String viewAttendance(@PathVariable Long id, Model model, Authentication auth) {
        User speaker = userRepository.findByEmail(auth.getName()).orElseThrow();
        Event event = eventRepository.findById(id).orElseThrow();

        boolean authorized = applicationService.findBySpeaker(speaker).stream()
                .anyMatch(a -> a.getEvent().getId().equals(id)
                        && a.getStatus() == PropertyApplication.Status.APPROVED);

        if (!authorized) {
            throw new org.springframework.security.access.AccessDeniedException(
                    "You are not approved to view attendance for this event.");
        }

        model.addAttribute("event", event);
        model.addAttribute("registrations", registrationService.findByEvent(event));
        return "speaker/attendance";
    }
}