package com.admission.controller;

import com.admission.entity.Student;
import com.admission.service.IStudentService;
import com.admission.util.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {

    @Autowired
    private IStudentService studentService;

    @GetMapping
    public PageResult<Student> getStudents(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size) {
        Page<Student> page = new Page<>(current, size);
        return studentService.getStudentPage(page);
    }

    @PostMapping
    public boolean addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping("/{examNumber}")
    public boolean updateStudent(@PathVariable String examNumber, @RequestBody Student student) {
        student.setExamNumber(examNumber);
        return studentService.updateStudent(student);
    }

    @DeleteMapping("/{examNumber}")
    public boolean deleteStudent(@PathVariable String examNumber) {
        return studentService.deleteStudent(examNumber);
    }

    @GetMapping("/search")
    public List<Student> searchStudents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String className,
            @RequestParam(required = false) Double minScore,
            @RequestParam(required = false) Double maxScore) {
        return studentService.searchStudents(name, className, minScore, maxScore);
    }
}