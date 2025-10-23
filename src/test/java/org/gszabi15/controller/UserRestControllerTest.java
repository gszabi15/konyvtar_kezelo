package org.gszabi15.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gszabi15.model.dto.UserDto;
import org.gszabi15.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
class UserRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    private static final UserDto testuser = new UserDto("test", "test@t.com", "test123", "");

    @Test
    void create() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(testuser)))
            .andExpect(status().isOk())
            .andExpect(content().string("User created successfully."));
    }

    @Test
    void update() throws Exception {
        create();
        UserDto user = new UserDto();
        user.setName("new_test");
        String token = jwtService.generateToken(testuser.getEmail());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/user/update")
                .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                    .andExpect(status().isOk())
                    .andExpect(content().string("User updated successfully."));
    }

    @Test
    void delete() throws Exception {
        create();
        String token = jwtService.generateToken(testuser.getEmail());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/delete")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("User deleted successfully."));
    }
}