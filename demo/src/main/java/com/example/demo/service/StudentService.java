package com.example.demo.service;

import org.springframework.stereotype.Service;
import java.util.List;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;

@Service // [cite: 244]
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) { // [cite: 247]
        this.repository = repository; // [cite: 249]
    }

    public List<Student> getAllStudents() { // [cite: 250]
        return repository.findAll(); // [cite: 254]
    }

    // Business logic to prevent duplicate emails 
    public void saveStudent(Student student) {
        if (repository.existsByEmail(student.getEmail())) { // 
            throw new RuntimeException("Email already exists!"); // [cite: 102, 184]
        }
        repository.save(student); // [cite: 257]
    }
}