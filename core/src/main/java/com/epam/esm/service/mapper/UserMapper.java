package com.epam.esm.service.mapper;

import com.epam.esm.persistence.repository.entity.UserEntity;
import com.epam.esm.service.dto.user.UserDTO;
import com.epam.esm.service.dto.user.UserPostDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toUser(UserPostDTO userDTO) {
        return new UserEntity()
                .setUsername(userDTO.getUsername())
                .setPassword(userDTO.getPassword());
    }

    public UserDTO toDTO(UserEntity user) {
        return new UserDTO()
                .setUsername(user.getUsername());
    }

}
