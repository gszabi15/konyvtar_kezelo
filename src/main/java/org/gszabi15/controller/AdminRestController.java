package org.gszabi15.controller;

import lombok.RequiredArgsConstructor;
import org.gszabi15.model.dto.UserDto;
import org.gszabi15.service.AdminService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;


import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminRestController {
    private final AdminService service;

    @PostMapping("/create")
    public UserDto create(@RequestBody UserDto dto) {
        return service.create(dto);
    }

    @GetMapping("/getByEmail/{email}")
    public UserDto getByEmail(@PathVariable("email") String email) {
        return service.getByEmail(email);
    }

    @PutMapping("/updateByEmail/{email}")
    public UserDto updateByEmail(@PathVariable("email") String email, @RequestBody UserDto dto) {
        return service.updateByEmail(email, dto);
    }

    @DeleteMapping("/deleteByEmail/{email}")
    public void deleteByEmail(@PathVariable("email") String email) {
        service.deleteByEmail(email);
    }

    @GetMapping("/getAllUser")
    public List<UserDto> getAllUser(){
        return service.getAllUser();
    }
}
