package com.contactpro.contactpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.contactpro.contactpro.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    /*
     * Custom method:
     * Get all contacts of a specific user
     */
    // For normal list
    List<Contact> findByUserId(Long userId);

    // For pagination
    Page<Contact> findByUserId(Long userId, Pageable pageable);

    Page<Contact> findByUserIdAndNameContainingIgnoreCase(
            Long userId,
            String name,
            Pageable pageable
    );

    Page<Contact> findByUserIdAndIsFavoriteTrue(
            Long userId,
            Pageable pageable
    );
}