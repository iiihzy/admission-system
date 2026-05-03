package com.admission.service;

import java.util.List;
import java.util.Map;

public interface IStatisticsService {
    /**
     * 统计各班级不同批次录取人数
     * @return 统计结果列表
     */
    List<Map<String, Object>> countAdmissionByClassAndBatch();

    /**
     * 查询被不同批次院校录取的学生信息
     * @return 学生信息列表
     */
    List<Map<String, Object>> getBatchAdmissionStudents();
}