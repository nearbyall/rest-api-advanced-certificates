package com.epam.esm.service;

import com.epam.esm.service.dto.user.UserDTO;
import com.epam.esm.service.dto.user.UserPostDTO;

import java.util.Optional;

/**
 * Service interface for business logic operations of the {@link UserDTO} class.
 */
public interface UserService {

    /**
     * Gets user from the repository by user id
     *
     * @param id user id
     * @return optional user
     */
    Optional<UserDTO> getById(Integer id);

    /**
     * Creates new user instance
     *
     * @param user user to create
     * @return optional id of the created user
     */
    Optional<Integer> create(UserPostDTO user);

    /**
     * Checks whether user is exists by username
     *
     * @param username user's username
     * @return true or false
     */
    boolean isExistByUsername(String username);
}

