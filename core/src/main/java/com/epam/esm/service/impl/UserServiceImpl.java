package com.epam.esm.service.impl;

import com.epam.esm.persistence.repository.UserRepository;
import com.epam.esm.persistence.repository.entity.UserEntity;
import com.epam.esm.service.UserService;
import com.epam.esm.service.aspect.Loggable;
import com.epam.esm.service.dto.user.UserDTO;
import com.epam.esm.service.dto.user.UserPostDTO;
import com.epam.esm.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<UserDTO> getById(Integer id) {
        Optional<UserEntity> user = userRepository.getById(id);

        return user.map(mapper::toDTO);
    }

    @Override
    @Loggable
    @Transactional
    public Optional<Integer> create(UserPostDTO userDTO) {
        UserEntity user = mapper.toUser(userDTO);

        return userRepository.create(user);
    }

    @Override
    public boolean isExistByUsername(String username) {
        return userRepository.isExistByUsername(username);
    }

}
