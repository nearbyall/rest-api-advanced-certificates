package com.epam.esm.service;

import com.epam.esm.persistence.repository.UserRepository;
import com.epam.esm.persistence.repository.entity.UserEntity;
import com.epam.esm.service.dto.user.UserDTO;
import com.epam.esm.service.dto.user.UserPostDTO;
import com.epam.esm.service.impl.UserServiceImpl;
import com.epam.esm.service.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static com.epam.esm.service.utils.UserServiceTestUtils.getUser;
import static com.epam.esm.service.utils.UserServiceTestUtils.getUserPostDTO;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Spy
    private UserMapper mapper;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl(userRepository, mapper);
    }

    @Test
    public void testGetById_shouldDelegateGettingToRepositoryIfUserFoundById() {
        int id = 1;
        UserEntity user = getUser().setId(id).setOrders(new ArrayList<>());
        UserDTO expectedUser = new UserDTO().setUsername(user.getUsername());

        when(userRepository.getById(id)).thenReturn(Optional.of(user));

        Optional<UserDTO> userDTO = userService.getById(id);

        verify(userRepository).getById(id);
        verify(mapper).toDTO(user);

        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(mapper);

        assertTrue(userDTO.isPresent());
        assertThat(userDTO.get(), is(equalTo(expectedUser)));
    }

    @Test
    public void testCreate_shouldDelegateCreationToRepositoryIfCreationIsSuccessful() {
        int createdId = 11;
        UserPostDTO userPostDTO = getUserPostDTO();
        UserEntity user =  getUser();

        when(userRepository.create(user)).thenReturn(Optional.of(createdId));

        Optional<Integer> userId = userService.create(userPostDTO);

        verify(userRepository).create(user);
        verify(mapper).toUser(userPostDTO);

        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(mapper);

        assertTrue(userId.isPresent());
        assertThat(userId.get(), is(equalTo(createdId)));
    }

    @Test
    public void testIsExistByUsername_shouldDelegateCheckingToRepositoryIfUserWasFound() {
        String username = "username";

        when(userRepository.isExistByUsername(username)).thenReturn(true);

        boolean isExist = userService.isExistByUsername(username);

        verify(userRepository).isExistByUsername(username);
        verifyNoMoreInteractions(userRepository);

        assertTrue(isExist);
    }

}
