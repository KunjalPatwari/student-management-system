package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import java.util.List;

@Controller
public class StudentController {
    private final StudentService service;
    public StudentController(StudentService service) { this.service = service; }

    // HTML View Mapping
    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("students", service.getAllStudents());
        model.addAttribute("student", new Student());
        return "index";
    }

    @PostMapping("/save")
    public String saveStudent(@ModelAttribute Student student, Model model) {
        try {
            service.saveStudent(student);
            return "redirect:/"; // Success! Go back to the list
        } catch (RuntimeException e) {
            // If duplicate exists, send the error message to the UI instead of crashing
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("students", service.getAllStudents());
            model.addAttribute("student", student); // Keep the data in the form
            return "index"; // Stay on the same page to show the error
        }
    }

    // REST API Mapping (Criteria 3)
    @GetMapping("/api/students")
    @ResponseBody
    public List<Student> getStudentsApi() { return service.getAllStudents(); }

    @PostMapping("/api/students")
    @ResponseBody
    public Student saveStudentApi(@RequestBody Student student) {
        service.saveStudent(student);
        return student;
    }
}