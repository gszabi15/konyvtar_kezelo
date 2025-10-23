package org.gszabi15.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gszabi15.model.dto.BookDto;
import org.gszabi15.model.dto.UserDto;
import org.gszabi15.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
class BookRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    private static final UserDto testuser = new UserDto("test", "test@t.com", "test123", "");
    private static final UserDto adminuser = new UserDto("admin", "admin@example.com", "admin123", "ROLE_USER,ROLE_ADMIN");
    private static BookDto testbook = new BookDto(null, "testBook", "testAuthor", true);


    @BeforeEach
    void setUp() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(testuser)))
                .andExpect(status().isOk())
                .andExpect(content().string("User created successfully."));

        String token = jwtService.generateToken(adminuser.getEmail());

        String responseBody = mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(testbook)))
                .andExpect(status().isOk()).andReturn()  // ResultActions -> MvcResult
                .getResponse()
                .getContentAsString();

        testbook = new ObjectMapper().readValue(responseBody, BookDto.class);
    }

    @Test
    void getAll() throws Exception {
        String token = jwtService.generateToken(testuser.getEmail());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books")
                .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getById() throws Exception {
        String token = jwtService.generateToken(testuser.getEmail());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/"+testbook.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void create() throws Exception {
        String token = jwtService.generateToken(adminuser.getEmail());

        String responseBody = mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(testbook)))
                .andExpect(status().isOk()).andReturn()  // ResultActions -> MvcResult
                .getResponse()
                .getContentAsString();

        testbook = new ObjectMapper().readValue(responseBody, BookDto.class);

    }

    @Test
    void update() throws Exception {
        String token = jwtService.generateToken(adminuser.getEmail());

        BookDto newbook = new BookDto(testbook.getId(), "new_testBook", "new_testAuthor", true);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/books/"+testbook.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newbook)))
                .andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        String token = jwtService.generateToken(adminuser.getEmail());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/"+testbook.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}