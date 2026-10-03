// repository/PropertyApplicationRepository.java
package com.example.demo.repository;

import com.example.demo.entity.PropertyApplication;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PropertyApplicationRepository extends JpaRepository<PropertyApplication, Long> {
    List<PropertyApplication> findBySpeaker(User speaker);

    boolean existsByProperty_Id(Long id);
}