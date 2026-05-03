package com.admission.controller;

import com.admission.entity.Admission;
import com.admission.service.IAdmissionService;
import com.admission.util.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admissions")
@CrossOrigin(origins = "http://localhost:3000")
public class AdmissionController {

    @Autowired
    private IAdmissionService admissionService;

    @GetMapping
    public PageResult<Admission> getAdmissions(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size) {
        // 使用自定义查询方法获取包含学生信息的录取数据
        return admissionService.getAdmissionPageWithStudentInfo(current, size);
    }

    @PostMapping
    public boolean addAdmission(@RequestBody Admission admission) {
        return admissionService.addAdmission(admission);
    }

    @PutMapping("/{id}")
    public boolean updateAdmission(@PathVariable Long id, @RequestBody Admission admission) {
        admission.setId(id);
        return admissionService.updateAdmission(admission);
    }

    @DeleteMapping("/{id}")
    public boolean deleteAdmission(@PathVariable Long id) {
        return admissionService.deleteAdmission(id);
    }

    @GetMapping("/search")
    public List<Admission> searchAdmissions(
            @RequestParam(required = false) String batch,
            @RequestParam(required = false) String universityName) {
        return admissionService.searchAdmissions(batch, universityName);
    }

    @GetMapping("/batch-students")
    public List<Admission> getBatchStudents() {
        return admissionService.getBatchStudents();
    }
}