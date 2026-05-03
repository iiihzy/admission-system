package com.admission.service.impl;

import com.admission.mapper.StatisticsMapper;
import com.admission.service.IStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StatisticsServiceImpl implements IStatisticsService {

    @Autowired
    private StatisticsMapper statisticsMapper;

    @Override
    public List<Map<String, Object>> countAdmissionByClassAndBatch() {
        return statisticsMapper.countAdmissionByClassAndBatch();
    }

    @Override
    public List<Map<String, Object>> getBatchAdmissionStudents() {
        return statisticsMapper.getBatchAdmissionStudents();
    }
}