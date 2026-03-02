package com.contactpro.contactpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.contactpro.contactpro.model.Contact;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    /*
     * Custom method:
     * Get all contacts of a specific user
     */
    List<Contact> findByUserId(Long userId);
}