package com.contactpro.contactpro.controller;

import org.springframework.data.domain.Page;
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

    @GetMapping("/user/{userId}/paginated")
    public Page<ContactResponse> getContactsPaginated(
            @PathVariable Long userId,
            @RequestParam int page,
            @RequestParam int size) {

        return contactService.getContactsByUserPaginated(userId, page, size);
    }

    @GetMapping("/search/{userId}")
    public Page<ContactResponse> searchContacts(
            @PathVariable Long userId,
            @RequestParam String keyword,
            @RequestParam int page,
            @RequestParam int size) {

        return contactService.searchContacts(userId, keyword, page, size);
    }

    @PutMapping("/{contactId}")
    public ContactResponse updateContact(
            @PathVariable Long contactId,
            @RequestParam Long userId,
            @RequestBody ContactRequest request) {

        return contactService.updateContact(contactId, userId, request);
    }

    @PatchMapping("/{contactId}/favorite")
    public ContactResponse toggleFavorite(
            @PathVariable Long contactId,
            @RequestParam Long userId) {

        return contactService.toggleFavorite(contactId, userId);
    }

    @DeleteMapping("/{contactId}")
    public String deleteContact(
            @PathVariable Long contactId,
            @RequestParam Long userId) {

        contactService.deleteContact(contactId, userId);
        return "Contact deleted successfully";
    }
}