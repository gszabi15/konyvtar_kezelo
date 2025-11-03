package org.gszabi15.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.gszabi15.mapper.EntityMapper;
import org.gszabi15.model.dto.UserDto;
import org.gszabi15.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository repo;
    private final EntityMapper mapper;

    public UserDto create(UserDto dto) {
        return mapper.userToDto(repo.save(mapper.dtoToUser(dto)));
    }

    public UserDto getByEmail(String email) {
        return repo.findByEmail(email).map(mapper::userToDto).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    @Transactional
    public void deleteByEmail(String email) {
        if (!repo.existsByEmail(email)) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        repo.deleteByEmail(email);
    }

    public UserDto updateByEmail(String email, UserDto dto) {
        return repo.findByEmail(email).map(u -> {
            if (repo.existsByEmail(dto.getEmail()) && !u.getEmail().equals(dto.getEmail())) {
                throw new UsernameNotFoundException("User already exists with email: " + dto.getEmail());
            }
            mapper.updateUserFromDto(dto, u);
            repo.save(u);
            return mapper.userToDto(u);
        }).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public List<UserDto> getAllUser(){
        return repo.findAll().stream().map(mapper::userToDto).toList();
    }
}
