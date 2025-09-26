package com.practice2.testning1.Student;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock private StudentRepository studentRepository;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentService(studentRepository);
    }


    @Test
    void canGetAllStudents() {
        // when
        studentService.getAllStudents();
        // then
        verify(studentRepository).findAll();
    }

    @Test
    void canAddStudent() {
        // given
        Student student = new Student(
                "Joel",
                "joel@gmail.com"
        );
        // when
        studentService.addStudent(student);

        // then
        ArgumentCaptor<Student> studentCaptor =
                ArgumentCaptor.forClass(Student.class);
        verify(studentRepository)
                .save(studentCaptor.capture());

        Student capturedStudent = studentCaptor.getValue();
        assertEquals(capturedStudent, student);
    }

    @Test
    void willThrowWhenEmailIsTaken() {
        // given
        Student student = new Student(
                "Joel",
                "joel@gmail.com"
        );

        given(studentRepository.existsByEmail(student.getEmail()))
                .willReturn(true);
        // when
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                () -> studentService.addStudent(student));

        // then
        assertEquals("Email " +student.getEmail()+
                " is already in use", exception.getMessage());

        verify(studentRepository, never()).save(any());

    }

    @Test
    void shouldDeleteStudent() {
        // given
        Long id = 1L;
        given(studentRepository.existsById(id)).willReturn(true);

        //when
        studentService.deleteStudent(id);

        //then
        verify(studentRepository).deleteById(id);

    }

    @Test
    void shouldThrowWhenIdDoesNotExist() {
        // given
        Long id = 1L;
        given(studentRepository.existsById(id)).willReturn(false);

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> studentService.deleteStudent(id));

        // then
        assertEquals("Student with id " + id + " does not exist",
                exception.getMessage());
        verify(studentRepository, never()).deleteById(any());
    }
}