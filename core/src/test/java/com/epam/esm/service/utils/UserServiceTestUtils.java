package com.epam.esm.service.utils;

import com.epam.esm.persistence.repository.entity.UserEntity;
import com.epam.esm.service.dto.user.UserDTO;
import com.epam.esm.service.dto.user.UserPostDTO;

public class UserServiceTestUtils {

    public static UserPostDTO getUserPostDTO() {
        return new UserPostDTO().setUsername("username").setPassword("password");
    }

    public static UserEntity getUser() {
        return new UserEntity().setUsername("username").setPassword("password");
    }

    public static UserDTO getUserDTO() {
        return new UserDTO().setUsername("username");
    }
}
