package org.gszabi15.service;

import org.gszabi15.model.dto.UserDto;
import org.gszabi15.mapper.EntityMapper;
import org.gszabi15.model.entity.User;
import org.gszabi15.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository repo;
    private final EntityMapper mapper;
    private final PasswordEncoder encoder;

    public String create(UserDto dto) {
        User user = mapper.dtoToUser(dto);
        user.setRoles("ROLE_USER");
        user.setPassword(encoder.encode(user.getPassword()));
        if (repo.existsByEmail(user.getEmail())) {
            throw new UsernameNotFoundException("User already exists with email: " + user.getEmail());
        }
        repo.save(user);
        return "User created successfully.";
    }

    public String update(UserDto dto) {
        return repo.findById(getCurrentUser().getId()).map(u -> {
            u.setRoles(getCurrentUser().getRoles());

            if (dto.getName() == null) {
                u.setName(getCurrentUser().getName());
            } else {
                u.setName(dto.getName());
            }

            if (dto.getEmail() == null || repo.existsByEmail(dto.getEmail())) {
                u.setEmail(getCurrentUser().getEmail());
            } else {
                u.setEmail(dto.getEmail());
            }

            if (dto.getPassword() == null) {
                u.setPassword(getCurrentUser().getPassword());
            } else {
                u.setPassword(encoder.encode(dto.getPassword()));
            }

            repo.save(u);
            return "User updated successfully.";
        }).orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + getCurrentUser().getId()));
    }

    public String delete() {
        if (!repo.existsById(getCurrentUser().getId())) {
            throw new UsernameNotFoundException("User not found with id: " + getCurrentUser().getId());
        }
        repo.deleteById(getCurrentUser().getId());
        return "User deleted successfully.";
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return repo.findByEmail(auth.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + auth.getName()));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = repo.findByEmail(email);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        User newuser = user.get();

        List<GrantedAuthority> authorities = Stream.of(newuser.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(newuser.getEmail(), newuser.getPassword(), authorities);
    }

}
