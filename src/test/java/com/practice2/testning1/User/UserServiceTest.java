package com.practice2.testning1.User;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void registerUser_ValidUser_ShouldSaveUser() throws UserAlreadyExistsException {
        // Arrange
        User expectedUser = new User("anna@test.com", "password123");
        expectedUser.setId(1L);
        when(userRepository.findByEmail("anna@test.com")).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        // Act
        User result = userService.registerUser("anna@test.com", "password123");

        // Assert
        assertEquals(1L, result.getId());
        assertEquals("anna@test.com", result.getEmail());

        // Verify
        verify(userRepository).findByEmail("anna@test.com");
        verify(userRepository).save(any(User.class));

    }

    @Test
    void registerUser_DuplicateEmail_ShouldThrowException() {
        // Arrange: Simulera att användaren redan finns
        User existingUser = new User("anna@test.com", "password123");
        when(userRepository.findByEmail("anna@test.com")).thenReturn(existingUser);

        // Act & Assert: Förvänta exception
        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.registerUser("anna@test.com", "password123");
        });

        // Verify: save() ska inte ha anropats
        verify(userRepository, never()).save(any(User.class));

    }

    @Test
    void registerUser_InvalidEmail_ShouldThrowException() {
        // given
        String email = "mail,com";

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.registerUser(email, "password123"));

        assertEquals("Ogiltig e-postadress.", exception.getMessage());
        // then
        verify(userRepository, never()).save(any(User.class));

    }

    @Test
    void findUserByEmail_ExistingEmail_ShouldReturnUser() throws UserAlreadyExistsException {
        //given
        String email = "joel@gmail.com";
        User user = new User(email, "password123");

        // when
        when(userRepository.findByEmail(email)).thenReturn(user);
        User result = userService.findUserByEmail(email);

        // then
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getEmail(), result.getEmail());
        verify(userRepository).findByEmail(email);

    }

    @Test
    void findUserByEmail_NonExistingUser_ShouldReturnNull() {
        // given
        String email = "joel@gmail.com";
        when(userRepository.findByEmail(email)).thenReturn(null);

        // when
        User result = userService.findUserByEmail(email);

        // then
        assertNull(result);
        verify(userRepository).findByEmail(email);
    }

}
