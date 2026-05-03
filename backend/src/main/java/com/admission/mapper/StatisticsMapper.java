package com.admission.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

@Mapper
public interface StatisticsMapper {

    /**
     * 调用存储过程统计各班级不同批次录取人数
     */
    @Select("CALL count_admission_by_class_and_batch()")
    List<Map<String, Object>> countAdmissionByClassAndBatch();

    /**
     * 查询视图：被不同批次院校录取的学生信息
     */
    @Select("SELECT * FROM view_batch_admission_students")
    List<Map<String, Object>> getBatchAdmissionStudents();
}