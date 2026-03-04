package com.contactpro.contactpro.repository;

import com.contactpro.contactpro.model.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * InteractionRepository
 *
 * Responsibility:
 * Communicates with the database for Interaction entity.
 *
 * JpaRepository automatically provides:
 * - save()
 * - findById()
 * - findAll()
 * - delete()
 * - update (via save)
 *
 * We only define extra queries if needed.
 */

@Repository
public interface InteractionRepository extends JpaRepository<Interaction, Long> {

    /*
     * Find all interactions for a specific contact
     *
     * Spring Data JPA automatically converts this
     * method name into SQL:
     *
     * SELECT * FROM interactions WHERE contact_id = ?
     *
     * This allows us to view interaction history
     * of a contact.
     */
    List<Interaction> findByContactId(Long contactId);

}