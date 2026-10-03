package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EventRegistrationService;
import com.example.demo.service.QrCodeService;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student")
public class StudentController {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventRegistrationService registrationService;
    private final QrCodeService qrCodeService;

    public StudentController(EventRepository eventRepository, UserRepository userRepository,
            EventRegistrationService registrationService, QrCodeService qrCodeService) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.registrationService = registrationService;
        this.qrCodeService = qrCodeService;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "student/dashboard";
    }

    @GetMapping("/events")
    public String viewEvents(Model model) {
        model.addAttribute("events", eventRepository.findAll());
        return "student/events";
    }

    @PostMapping("/events/register/{eventId}")
    public String register(@PathVariable Long eventId, Authentication auth, Model model) {
        User student = userRepository.findByEmail(auth.getName()).orElseThrow();
        Event event = eventRepository.findById(eventId).orElseThrow();
        try {
            registrationService.register(student, event);
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("events", eventRepository.findAll());
            return "student/events";
        }
        return "redirect:/student/registrations";
    }

    @GetMapping("/registrations")
    public String myRegistrations(Model model, Authentication auth) {
        User student = userRepository.findByEmail(auth.getName()).orElseThrow();
        model.addAttribute("registrations", registrationService.findByStudent(student));
        return "student/registrations";
    }

    @GetMapping(value = "/registrations/{id}/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] getQrCode(@PathVariable Long id) throws Exception {
        EventRegistration reg = registrationService.findById(id);
        return qrCodeService.generateQrCodePng(reg.getTicketCode(), 250);
    }
}