package com.epam.esm.controller;


import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.message.ApiStatusCode;
import com.epam.esm.exception.message.MessageFactory;
import com.epam.esm.persistence.repository.entity.Role;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.user.UserDTO;
import com.epam.esm.service.dto.user.UserPostDTO;
import com.epam.esm.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import java.util.Optional;

/**
 * REST Controller class for operations upon the users.
 * It delegates tasks to the {@link UserService} class.
 * Uses {@link MessageFactory} for exception messages
 * and {@link  Validator} for the validation of the DTO objects.
 */
@RestController
@RequestMapping(value = "/api/V1/users")
public class UserController {

    private final UserService userService;

    private final MessageFactory messageFactory;

    private final Validator validator;

    @Autowired
    public UserController(UserService userService, MessageFactory messageFactory, Validator validator) {
        this.userService = userService;
        this.messageFactory = messageFactory;
        this.validator = validator;
    }

    /**
     * GET Controller for retrieving user by id
     *
     * @param id user id
     * @return ResponseEntity of the {@link UserDTO} object
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Integer id) {
        Optional<UserDTO> user = userService.getById(id);

        if (!user.isPresent()) {
            throw new BadRequestException(
                    messageFactory.getNotFoundById(id), ApiStatusCode.USER_NOT_FOUND, HttpStatus.NOT_FOUND
            );
        }

        return ResponseEntity.ok(user.get());
    }

    /**
     * POST Controller for new user registration
     *
     * @param userDTO       DTO with validation fields
     * @param bindingResult if the DTO has validation errors binding result will contain them
     * @return ResponseEntity of the {@link UserDTO} object with a username of a created user
     */
    @PostMapping(value = "/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserPostDTO userDTO, BindingResult bindingResult) {
        validator.checkErrorsInBindingResult(bindingResult, ApiStatusCode.USER_BAD_REQUEST);

        boolean isExist = userService.isExistByUsername(userDTO.getUsername());

        if (isExist) {
            throw new BadRequestException(
                    messageFactory.getUserExist(userDTO.getUsername()), ApiStatusCode.USER_BAD_REQUEST, HttpStatus.BAD_REQUEST
            );
        }

        Optional<Integer> id = userService.create(userDTO.setRole(Role.USER));

        if (!id.isPresent()) {
            throw new BadRequestException(
                    messageFactory.getNotAdded(), ApiStatusCode.USER_BAD_REQUEST, HttpStatus.BAD_REQUEST
            );
        }

        return getById(id.get());
    }

}
