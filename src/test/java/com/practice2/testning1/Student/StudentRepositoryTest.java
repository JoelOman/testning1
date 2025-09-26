package com.practice2.testning1.Student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    void itShouldCheckIfStudentEmailExists() {
        // given
        String name = "Joel";
        String email = "joel@gmail.com";
        Student student = new Student(name, email);
        studentRepository.save(student);

        // when
        boolean expected = studentRepository.existsByEmail(email);

        // then
        assertTrue(expected);
    }
    @Test
    void itShouldCheckIfStudentEmailDoesNotExists() {
        // given
        String email = "joel@gmail.com";

        // when
        boolean expected = studentRepository.existsByEmail(email);

        // then
        assertFalse(expected);
    }

}