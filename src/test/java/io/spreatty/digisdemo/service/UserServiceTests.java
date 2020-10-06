package io.spreatty.digisdemo.service;

import io.spreatty.digisdemo.converter.GenderConverter;
import io.spreatty.digisdemo.converter.UserConverter;
import io.spreatty.digisdemo.data.UserMapper;
import io.spreatty.digisdemo.exception.UserNotFoundException;
import io.spreatty.digisdemo.pojo.dto.UserDTO;
import io.spreatty.digisdemo.pojo.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class UserServiceTests {
    @Mock
    private UserMapper userMapper;

    @Spy
    private UserConverter userConverter;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void beforeEach() {
        userConverter.setGenderConverter(new GenderConverter());
    }

    @Test
    public void userCreated() {
        Mockito.when(userMapper.getUserByLogin(Mockito.any())).thenReturn(null);
        Mockito.when(userMapper.saveUser(Mockito.any())).then(invocation -> {
           invocation.getArgument(0, User.class).setId(0);
           return 1;
        });
        Optional<UserDTO> answer = userService.createUser(new UserDTO());
        Assertions.assertTrue(answer.isPresent());
        Assertions.assertNotNull(answer.get().getId());
    }

    @Test
    public void noUserCreatedLoginAlreadyUsed() {
        Mockito.when(userMapper.getUserByLogin(Mockito.any())).thenReturn(new User());
        Assertions.assertFalse(userService.createUser(new UserDTO()).isPresent());
    }

    @Test
    public void userUpdated() {
        User user = new User();
        user.setLogin("");
        Mockito.when(userMapper.getUser(Mockito.anyInt())).thenReturn(user);
        Mockito.when(userMapper.getUserByLogin(Mockito.any())).thenReturn(null);
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin("");
        Assertions.assertTrue(userService.updateUser(0, userDTO).isPresent());
    }

    @Test
    public void noUserUpdatedLoginAlreadyUsed() {
        User user = new User();
        user.setLogin("");
        Mockito.when(userMapper.getUser(Mockito.anyInt())).thenReturn(user);
        Mockito.when(userMapper.getUserByLogin(Mockito.any())).thenReturn(new User());
        Assertions.assertFalse(userService.updateUser(0, new UserDTO()).isPresent());
    }

    @Test
    public void noUserUpdatedUserNotFound() {
        Mockito.when(userMapper.getUser(Mockito.anyInt())).thenReturn(null);
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.updateUser(0, new UserDTO()));
    }
}
