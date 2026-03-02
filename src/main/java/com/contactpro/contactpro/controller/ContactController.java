package com.contactpro.contactpro.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.contactpro.contactpro.service.ContactService;
import com.contactpro.contactpro.dto.ContactRequest;
import com.contactpro.contactpro.dto.ContactResponse;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    /*
     * Create Contact
     */
    @PostMapping
    public ContactResponse createContact(@RequestBody ContactRequest request) {
        return contactService.createContact(request);
    }

    /*
     * Get contacts of user
     */
    @GetMapping("/user/{userId}")
    public List<ContactResponse> getContactsByUser(@PathVariable Long userId) {
        return contactService.getContactsByUser(userId);
    }
}