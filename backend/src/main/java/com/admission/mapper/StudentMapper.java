package com.admission.mapper;

import com.admission.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    List<Student> searchStudents(@Param("name") String name,
                                @Param("className") String className,
                                @Param("minScore") Double minScore,
                                @Param("maxScore") Double maxScore);
}