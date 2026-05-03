package com.admission.controller;

import com.admission.entity.Admission;
import com.admission.entity.Student;
import com.admission.service.IAdmissionService;
import com.admission.service.IStudentService;
import com.admission.service.IUniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
@CrossOrigin(origins = "http://localhost:3000")
public class StatisticsController {

    @Autowired
    private IStudentService studentService;

    @Autowired
    private IUniversityService universityService;

    @Autowired
    private IAdmissionService admissionService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/batch-count")
    public List<Map<String, Object>> getBatchCount() {
        String sql = "CALL count_admission_by_class_and_batch()";
        return jdbcTemplate.queryForList(sql);
    }

    @GetMapping("/batch-students")
    public List<Admission> getBatchStudents() {
        return admissionService.getBatchStudents();
    }

    @GetMapping("/overview")
    public Map<String, Object> getOverview() {
        Map<String, Object> overview = new java.util.HashMap<>();
        overview.put("totalStudents", studentService.count());
        overview.put("totalUniversities", universityService.count());
        overview.put("totalAdmissions", admissionService.count());
        return overview;
    }
}