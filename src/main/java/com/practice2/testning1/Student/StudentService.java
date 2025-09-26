package com.practice2.testning1.Student;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public void addStudent(Student student){
        boolean existsEmail = studentRepository
                .existsByEmail(student.getEmail());
        if(existsEmail){
            throw new IllegalArgumentException("Email " +student.getEmail()+ " is already in use");
        }

        studentRepository.save(student);

    }
    public void deleteStudent(Long id){
        if(!studentRepository.existsById(id)){
            throw new IllegalArgumentException("Student with id " +id + " does not exist");
        }
        studentRepository.deleteById(id);
    }

}
