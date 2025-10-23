package org.gszabi15.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gszabi15.model.dto.BookDto;
import org.gszabi15.model.dto.UserDto;
import org.gszabi15.model.entity.BorrowRequest;
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
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
class LoanRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    private static final UserDto adminuser = new UserDto("admin", "admin@example.com", "admin123", "ROLE_USER,ROLE_ADMIN");
    private static final UserDto testuser = new UserDto("test", "test@t.com", "test123", "");
    private static BookDto testbook = new BookDto(null, "testBook", "testAuthor", true);

    private UUID loanid;

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
                .andExpect(status().isOk()).andReturn()
                .getResponse()
                .getContentAsString();

        testbook = new ObjectMapper().readValue(responseBody, BookDto.class);

    }

    @Test
    void borrow() throws Exception {
        String token = jwtService.generateToken(testuser.getEmail());

        BorrowRequest requestBody = new BorrowRequest(UUID.fromString(testbook.getId()), null, 14);

        String responseBody = mockMvc.perform(MockMvcRequestBuilders.post("/api/loans/borrow")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody)))
                .andExpect(status().isOk()).andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(responseBody);
        loanid = UUID.fromString(node.get("id").asText());
    }

    @Test
    void borrowAdmin() throws Exception {
        String token = jwtService.generateToken(adminuser.getEmail());

        BorrowRequest requestBody = new BorrowRequest(UUID.fromString(testbook.getId()), testuser.getEmail(), 14);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/loans/admin/borrow")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody)))
                .andExpect(status().isOk());

    }

    @Test
    void returnLoan() throws Exception {
        borrow();
        String token = jwtService.generateToken(testuser.getEmail());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/loans/"+ loanid.toString() +"/return")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void returnLoanAdmin() throws Exception {
        borrow();
        String token = jwtService.generateToken(adminuser.getEmail());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/loans/admin/"+ loanid.toString() +"/return")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void expired() throws Exception {

        String token = jwtService.generateToken(testuser.getEmail());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/loans/expired")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void expiredAdmin() throws Exception {
        String token = jwtService.generateToken(adminuser.getEmail());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/loans/admin/expired")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}