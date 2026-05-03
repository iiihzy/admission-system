package com.admission.service.impl;

import com.admission.entity.Student;
import com.admission.mapper.StudentMapper;
import com.admission.service.IStudentService;
import com.admission.util.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Override
    public PageResult<Student> getStudentPage(Page<Student> page) {
        Page<Student> result = page(page);
        return new PageResult<>(result.getRecords(), result.getTotal(), result.getSize(), result.getCurrent());
    }

    @Override
    public boolean addStudent(Student student) {
        return save(student);
    }

    @Override
    public boolean updateStudent(Student student) {
        return updateById(student);
    }

    @Override
    public boolean deleteStudent(String examNumber) {
        return removeById(examNumber);
    }

    @Override
    public List<Student> searchStudents(String name, String className, Double minScore, Double maxScore) {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();

        if (name != null && !name.isEmpty()) {
            queryWrapper.like(Student::getName, name);
        }

        if (className != null && !className.isEmpty()) {
            queryWrapper.eq(Student::getClassName, className);
        }

        if (minScore != null) {
            queryWrapper.ge(Student::getGaokaoScore, minScore);
        }

        if (maxScore != null) {
            queryWrapper.le(Student::getGaokaoScore, maxScore);
        }

        queryWrapper.orderByDesc(Student::getGaokaoScore);

        return list(queryWrapper);
    }
}