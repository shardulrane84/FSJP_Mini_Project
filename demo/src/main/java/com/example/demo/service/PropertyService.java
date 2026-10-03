package com.example.demo.service;

import com.example.demo.entity.Property;
import com.example.demo.repository.PropertyApplicationRepository;
import com.example.demo.repository.PropertyRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyApplicationRepository propertyApplicationRepository;

    public PropertyService(PropertyRepository propertyRepository,
            PropertyApplicationRepository propertyApplicationRepository) {
        this.propertyRepository = propertyRepository;
        this.propertyApplicationRepository = propertyApplicationRepository;
    }

    public Property create(Property property) {
        return propertyRepository.save(property);
    }

    public List<Property> findAll() {
        return propertyRepository.findAll();
    }

    public Property findById(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));
    }

    public Property update(Long id, Property updated) {
        Property existing = findById(id);
        existing.setPropertyName(updated.getPropertyName());
        existing.setDescription(updated.getDescription());
        existing.setLocation(updated.getLocation());
        existing.setCapacity(updated.getCapacity());
        existing.setAvailable(updated.getAvailable());
        return propertyRepository.save(existing);
    }

    public void delete(Long id) {
        boolean hasApplications = propertyApplicationRepository.existsByProperty_Id(id);
        if (hasApplications) {
            throw new IllegalStateException(
                    "Cannot delete this property — it has existing applications. Remove or reassign them first.");
        }
        propertyRepository.deleteById(id);
    }
}