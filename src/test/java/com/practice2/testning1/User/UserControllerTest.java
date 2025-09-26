package com.practice2.testning1.User;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void createUser_ValidRequest_ShouldReturn201() throws Exception, UserAlreadyExistsException {
        // Arrange
        User user = new User("joel@gmail.com", "password123");
        when(userService.registerUser("joel@gmail.com", "password123")).thenReturn(user);

        // Act & Assert
        mockMvc.perform(post("/users/register")
                .param("email", "joel@gmail.com")
                .param("password", "password123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("@.email").value("joel@gmail.com"));

        // Verify
        verify(userService).registerUser("joel@gmail.com", "password123");
    }
}