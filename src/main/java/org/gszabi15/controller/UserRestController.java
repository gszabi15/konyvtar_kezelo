package org.gszabi15.controller;

import org.gszabi15.model.dto.UserDto;
import org.gszabi15.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService service;

    @PostMapping("/create")
    public String create(@RequestBody UserDto dto) {
        return service.create(dto);
    }

    @PutMapping("/update")
    public String update(@RequestBody UserDto dto) {
        return service.update(dto);
    }

    @DeleteMapping("/delete")
    public String delete() {
        return service.delete();
    }
}
