package com.example.demo.service;

import com.example.demo.entity.PropertyApplication;
import com.example.demo.entity.User;
import com.example.demo.repository.PropertyApplicationRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PropertyApplicationService {

    private final PropertyApplicationRepository repo;

    public PropertyApplicationService(PropertyApplicationRepository repo) {
        this.repo = repo;
    }

    public PropertyApplication apply(PropertyApplication application) {
        return repo.save(application); // status defaults to PENDING
    }

    public List<PropertyApplication> findAll() {
        return repo.findAll();
    }

    public List<PropertyApplication> findBySpeaker(User speaker) {
        return repo.findBySpeaker(speaker);
    }

    public PropertyApplication approve(Long id) {
        PropertyApplication app = repo.findById(id).orElseThrow();
        app.setStatus(PropertyApplication.Status.APPROVED);
        return repo.save(app);
    }

    public PropertyApplication reject(Long id) {
        PropertyApplication app = repo.findById(id).orElseThrow();
        app.setStatus(PropertyApplication.Status.REJECTED);
        return repo.save(app);
    }
}