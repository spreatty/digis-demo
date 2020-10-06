package io.spreatty.digisdemo.service;

import io.spreatty.digisdemo.converter.Converter;
import io.spreatty.digisdemo.data.UserMapper;
import io.spreatty.digisdemo.exception.UserNotFoundException;
import io.spreatty.digisdemo.pojo.dto.UserDTO;
import io.spreatty.digisdemo.pojo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Converter<UserDTO, User> userConverter;

    public Optional<UserDTO> createUser(UserDTO userDTO) {
        if (userMapper.getUserByLogin(userDTO.getLogin()) != null) {
            return Optional.empty();
        }
        User user = userConverter.fromDTO(userDTO);
        userMapper.saveUser(user);
        return Optional.of(userConverter.toDTO(user));
    }

    public Optional<UserDTO> updateUser(int id, UserDTO userDTO) {
        User storedUser = userMapper.getUser(id);
        if (storedUser == null) {
            throw new UserNotFoundException();
        }
        if (!storedUser.getLogin().equals(userDTO.getLogin()) &&
                userMapper.getUserByLogin(userDTO.getLogin()) != null) {
            return Optional.empty();
        }
        User user = userConverter.fromDTO(userDTO);
        user.setId(id);
        userMapper.updateUser(user);
        return Optional.of(userConverter.toDTO(user));
    }

    public UserDTO getUser(int id) {
        User user = userMapper.getUser(id);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return userConverter.toDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        return userMapper.getAllUsers().stream()
                .map(userConverter::toDTO)
                .collect(Collectors.toList());
    }
}
