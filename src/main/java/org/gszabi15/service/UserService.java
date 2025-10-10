package org.gszabi15.service;

import org.gszabi15.model.UserDto;
import org.gszabi15.mapper.EntityMapper;
import org.gszabi15.model.User;
import org.gszabi15.repository.UserRepository;

import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository repo;
    private final EntityMapper mapper;

    public UserService(UserRepository repo, EntityMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public UserDto create(UserDto dto) {
        if (dto.getId() == null || dto.getId().isBlank()) dto.setId(UUID.randomUUID().toString());
        User user = mapper.dtoToUser(dto);
        repo.save(user);
        return mapper.userToDto(user);
    }

    public UserDto getById(String id) {
        return repo.findById(id).map(mapper::userToDto).orElse(null);
    }

    public boolean delete(String id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    public UserDto update(String id, UserDto dto) {
        return repo.findById(id).map(u -> {
            mapper.updateUserFromDto(dto, u);
            repo.save(u);
            return mapper.userToDto(u);
        }).orElse(null);
    }
}
