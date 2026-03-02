package com.contactpro.contactpro.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import com.contactpro.contactpro.repository.ContactRepository;
import com.contactpro.contactpro.repository.UserRepository;
import com.contactpro.contactpro.model.Contact;
import com.contactpro.contactpro.model.User;
import com.contactpro.contactpro.dto.ContactRequest;
import com.contactpro.contactpro.dto.ContactResponse;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    public ContactService(ContactRepository contactRepository,
                          UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    /*
     * Create new contact for a specific user
     */
    public ContactResponse createContact(ContactRequest request) {

        // Find user first
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Contact contact = new Contact();
        contact.setName(request.getName());
        contact.setPhone(request.getPhone());
        contact.setEmail(request.getEmail());
        contact.setCategory(request.getCategory());
        contact.setNotes(request.getNotes());
        contact.setUser(user);

        Contact saved = contactRepository.save(contact);

        return new ContactResponse(
                saved.getId(),
                saved.getName(),
                saved.getPhone(),
                saved.getEmail(),
                saved.getCategory(),
                saved.isBlocked(),
                saved.isFavorite(),
                saved.getCreatedAt()
        );
    }

    /*
     * Get all contacts of a specific user
     */
    public List<ContactResponse> getContactsByUser(Long userId) {

        return contactRepository.findByUserId(userId)
                .stream()
                .map(contact -> new ContactResponse(
                        contact.getId(),
                        contact.getName(),
                        contact.getPhone(),
                        contact.getEmail(),
                        contact.getCategory(),
                        contact.isBlocked(),
                        contact.isFavorite(),
                        contact.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }
}