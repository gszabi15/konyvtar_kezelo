package org.gszabi15.service;

import org.gszabi15.model.dto.UserDto;
import org.gszabi15.mapper.EntityMapper;
import org.gszabi15.model.entity.User;
import org.gszabi15.repository.UserRepository;
import org.gszabi15.exceptions.UserNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repo;
    private final EntityMapper mapper;

    public UserDto create(UserDto dto) {
        User user = mapper.dtoToUser(dto);
        repo.save(user);
        return mapper.userToDto(user);
    }

    public UserDto getById(String id) {
        return repo.findById(id).map(mapper::userToDto).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    public void delete(String id) {
        if (!repo.existsById(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        repo.deleteById(id);
    }

    public UserDto update(String id, UserDto dto) {
        return repo.findById(id).map(u -> {
            mapper.updateUserFromDto(dto, u);
            repo.save(u);
            return mapper.userToDto(u);
        }).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }
}
