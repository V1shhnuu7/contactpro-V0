package com.contactpro.contactpro.service;

import org.springframework.stereotype.Service;
import com.contactpro.contactpro.repository.UserRepository;
import com.contactpro.contactpro.model.User;
import com.contactpro.contactpro.dto.UserRequest;
import com.contactpro.contactpro.dto.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

/*
 * @Service means:
 * This class contains business logic.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    /*
     * Constructor injection.
     * Spring automatically injects UserRepository.
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
     * Create new user
     */
    public UserResponse createUser(UserRequest request) {

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        User saved = userRepository.save(user);

        return new UserResponse(
                saved.getId(),
                saved.getName(),
                saved.getEmail()
        );
    }

    /*
     * Get all users
     */
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                ))
                .collect(Collectors.toList());
    }
}