package com.admission.service;

import com.admission.entity.Student;
import com.admission.util.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IStudentService extends IService<Student> {
    PageResult<Student> getStudentPage(Page<Student> page);

    boolean addStudent(Student student);

    boolean updateStudent(Student student);

    boolean deleteStudent(String examNumber);

    List<Student> searchStudents(String name, String className, Double minScore, Double maxScore);
}