package com.practice2.testning1.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;


    @Test
    void save_ValidUser_ShouldPersistUser(){
        // given
        User user = new User("joel@gmail.com", "");

        // when
        User savedUser = userRepository.save(user);

        // then
        assertNotNull(savedUser.getId());
        Optional<User> result = userRepository.findById(user.getId());
        assertTrue(result.isPresent());
        assertEquals(savedUser.getId(), result.get().getId());

        User persisted = entityManager.find(User.class, savedUser.getId());
        assertNotNull(persisted);
        assertEquals(savedUser.getId(), persisted.getId());
        assertEquals("joel@gmail.com", persisted.getEmail());
    }

    @Test
    void findByEmail_ExistingUser_ShouldReturnUser(){
        // given
        User existingUser = new User("joel@gmail.com", "");
        entityManager.persistAndFlush(existingUser);

        // when
        User foundUser = userRepository.findByEmail("joel@gmail.com");

        // then
        assertNotNull(foundUser);
        assertEquals(existingUser.getId(), foundUser.getId());
        assertEquals(existingUser.getEmail(), foundUser.getEmail());
    }

    @Test
    void findByEmail_NonExistingUser_ShouldReturnNull(){
        // given
        User existingUser = new User("joel@gmail.com", "");
        entityManager.persistAndFlush(existingUser);

        // when
        User nonExistingUser = userRepository.findByEmail("nonExisting@gmail.com");

        // then
        assertNull(nonExistingUser);
    }

    @Test
    void deleteUser_ShouldRemoveFromDatabase() {
        // given
        User existingUser = new User("joel@gmail.com", "password123");
        userRepository.saveAndFlush(existingUser);

        // when
        userRepository.delete(existingUser);
        userRepository.flush();

        // then
        User deletedUser = entityManager.find(User.class, existingUser.getId());
        assertNull(deletedUser);

    }

}