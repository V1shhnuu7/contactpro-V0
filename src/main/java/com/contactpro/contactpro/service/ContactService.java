package com.contactpro.contactpro.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public Page<ContactResponse> getContactsByUserPaginated(
            Long userId,
            int page,
            int size) {

        Page<Contact> contactPage =
                contactRepository.findByUserId(
                        userId,
                        PageRequest.of(page, size)
                );

        return contactPage.map(contact ->
                new ContactResponse(
                        contact.getId(),
                        contact.getName(),
                        contact.getPhone(),
                        contact.getEmail(),
                        contact.getCategory(),
                        contact.isBlocked(),
                        contact.isFavorite(),
                        contact.getCreatedAt()
                )
        );
    }

    public Page<ContactResponse> searchContacts(
            Long userId,
            String keyword,
            int page,
            int size) {

        Page<Contact> contactPage =
                contactRepository.findByUserIdAndNameContainingIgnoreCase(
                        userId,
                        keyword,
                        PageRequest.of(page, size)
                );

        return contactPage.map(contact ->
                new ContactResponse(
                        contact.getId(),
                        contact.getName(),
                        contact.getPhone(),
                        contact.getEmail(),
                        contact.getCategory(),
                        contact.isBlocked(),
                        contact.isFavorite(),
                        contact.getCreatedAt()
                )
        );
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

    public ContactResponse updateContact(Long contactId,
                                         Long userId,
                                         ContactRequest request) {

        // Find contact
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found"));

        // Validate ownership
        if (!contact.getUser().getId().equals(userId)) {
            throw new RuntimeException("You are not allowed to update this contact");
        }

        // Update allowed fields
        contact.setName(request.getName());
        contact.setPhone(request.getPhone());
        contact.setEmail(request.getEmail());
        contact.setCategory(request.getCategory());
        contact.setNotes(request.getNotes());

        Contact updated = contactRepository.save(contact);

        return new ContactResponse(
                updated.getId(),
                updated.getName(),
                updated.getPhone(),
                updated.getEmail(),
                updated.getCategory(),
                updated.isBlocked(),
                updated.isFavorite(),
                updated.getCreatedAt()
        );
    }

    public ContactResponse toggleFavorite(Long contactId, Long userId) {

        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found"));

        // Ownership validation
        if (!contact.getUser().getId().equals(userId)) {
            throw new RuntimeException("You are not allowed to modify this contact");
        }

        // Toggle favorite
        contact.setFavorite(!contact.isFavorite());

        Contact updated = contactRepository.save(contact);

        return new ContactResponse(
                updated.getId(),
                updated.getName(),
                updated.getPhone(),
                updated.getEmail(),
                updated.getCategory(),
                updated.isBlocked(),
                updated.isFavorite(),
                updated.getCreatedAt()
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

    public void deleteContact(Long contactId, Long userId) {

        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found"));

        // Ownership validation
        if (!contact.getUser().getId().equals(userId)) {
            throw new RuntimeException("You are not allowed to delete this contact");
        }

        contactRepository.delete(contact);
    }
}