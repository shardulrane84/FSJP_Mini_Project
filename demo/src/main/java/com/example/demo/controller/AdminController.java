package com.example.demo.controller;

import com.example.demo.entity.Event;
import com.example.demo.entity.EventRegistration;
import com.example.demo.entity.Property;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EventRegistrationService;
import com.example.demo.service.EventService;
import com.example.demo.service.PropertyApplicationService;
import com.example.demo.service.PropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PropertyService propertyService;
    private final UserRepository userRepository;
    private final EventService eventService;
    private final PropertyApplicationService applicationService;
    private final EventRegistrationService registrationService;

    public AdminController(PropertyService propertyService, UserRepository userRepository, EventService eventService,
            PropertyApplicationService applicationService, EventRegistrationService registrationService) {
        this.propertyService = propertyService;
        this.userRepository = userRepository;
        this.eventService = eventService;
        this.applicationService = applicationService;
        this.registrationService = registrationService;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/users";
    }

    @GetMapping("/properties")
    public String listProperties(Model model) {
        model.addAttribute("properties", propertyService.findAll());
        model.addAttribute("newProperty", new Property());
        return "admin/properties";
    }

    @PostMapping("/properties/create")
    public String createProperty(@ModelAttribute Property newProperty) {
        propertyService.create(newProperty);
        return "redirect:/admin/properties";
    }

    @PostMapping("/properties/update/{id}")
    public String updateProperty(@PathVariable Long id, @ModelAttribute Property property) {
        propertyService.update(id, property);
        return "redirect:/admin/properties";
    }

    @GetMapping("/applications")
    public String listApplications(Model model) {
        model.addAttribute("applications", applicationService.findAll());
        return "admin/applications";
    }

    @PostMapping("/applications/approve/{id}")
    public String approve(@PathVariable Long id) {
        applicationService.approve(id);
        return "redirect:/admin/applications";
    }

    @PostMapping("/applications/reject/{id}")
    public String reject(@PathVariable Long id) {
        applicationService.reject(id);
        return "redirect:/admin/applications";
    }

    @GetMapping("/events")
    public String listEvents(Model model) {
        model.addAttribute("events", eventService.findAll());
        model.addAttribute("newEvent", new Event());
        return "admin/events";
    }

    @PostMapping("/events/create")
    public String createEvent(@Valid @ModelAttribute("newEvent") Event newEvent,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("events", eventService.findAll());
            return "admin/events"; // re-show form with errors
        }
        eventService.create(newEvent);
        return "redirect:/admin/events";
    }

    @PostMapping("/events/cancel/{id}")
    public String cancelEvent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            eventService.cancel(id);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/events";
    }

    @GetMapping("/properties/edit/{id}")
    public String editPropertyForm(@PathVariable Long id, Model model) {
        model.addAttribute("editProperty", propertyService.findById(id));
        return "admin/property-edit";
    }

    @PostMapping("/properties/delete/{id}")
    public String deleteProperty(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            propertyService.delete(id);
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/properties";
    }

    @GetMapping("/events/{id}/attendance")
    public String attendance(@PathVariable Long id, Model model) {
        Event event = eventService.findById(id);
        model.addAttribute("event", event);
        model.addAttribute("registrations", registrationService.findByEvent(event));
        return "admin/attendance";
    }

    @GetMapping("/attendance")
    public String attendanceScanner() {
        return "admin/attendance";
    }

    @PostMapping("/attendance/mark")
    @ResponseBody
    public String markAttendance(@RequestParam String ticketCode) {
        try {
            EventRegistration reg = registrationService.markAttendance(ticketCode);
            return "OK: Attendance confirmed for " + reg.getStudent().getName()
                    + " (checked in at " + reg.getAttendedAt() + ").";
        } catch (IllegalArgumentException | IllegalStateException e) {
            return "ERROR: " + e.getMessage();
        }
    }
}
