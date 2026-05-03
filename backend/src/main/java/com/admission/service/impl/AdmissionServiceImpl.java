package com.admission.service.impl;

import com.admission.entity.Admission;
import com.admission.mapper.AdmissionMapper;
import com.admission.service.IAdmissionService;
import com.admission.util.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class AdmissionServiceImpl extends ServiceImpl<AdmissionMapper, Admission> implements IAdmissionService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public PageResult<Admission> getAdmissionPage(Page<Admission> page) {
        Page<Admission> result = page(page);
        return new PageResult<>(result.getRecords(), result.getTotal(), result.getSize(), result.getCurrent());
    }

    @Override
    public PageResult<Admission> getAdmissionPageWithStudentInfo(Long current, Long size) {
        String sql = "SELECT a.*, s.class_name, s.gaokao_score " +
                     "FROM admission a " +
                     "LEFT JOIN student s ON a.exam_number = s.exam_number " +
                     "ORDER BY a.create_time DESC";

        String countSql = "SELECT COUNT(*) FROM admission";

        long total = jdbcTemplate.queryForObject(countSql, Long.class);

        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(
            sql + " LIMIT ?, ?", (current - 1) * size, size);

        List<Admission> admissions = resultList.stream().map(map -> {
            Admission admission = new Admission();
            admission.setId(((Number) map.get("id")).longValue());
            admission.setExamNumber((String) map.get("exam_number"));
            admission.setName((String) map.get("name"));
            admission.setUniversityId((String) map.get("university_id"));
            admission.setUniversityName((String) map.get("university_name"));
            admission.setAdmissionBatch((String) map.get("admission_batch"));

            Object admissionTime = map.get("admission_time");
            if (admissionTime != null) {
                if (admissionTime instanceof LocalDateTime) {
                    admission.setAdmissionTime((LocalDateTime) admissionTime);
                } else if (admissionTime instanceof java.sql.Timestamp) {
                    admission.setAdmissionTime(((java.sql.Timestamp) admissionTime).toLocalDateTime());
                }
            }

            Object createTime = map.get("create_time");
            if (createTime != null) {
                if (createTime instanceof LocalDateTime) {
                    admission.setCreateTime((LocalDateTime) createTime);
                } else if (createTime instanceof java.sql.Timestamp) {
                    admission.setCreateTime(((java.sql.Timestamp) createTime).toLocalDateTime());
                }
            }

            Object updateTime = map.get("update_time");
            if (updateTime != null) {
                if (updateTime instanceof LocalDateTime) {
                    admission.setUpdateTime((LocalDateTime) updateTime);
                } else if (updateTime instanceof java.sql.Timestamp) {
                    admission.setUpdateTime(((java.sql.Timestamp) updateTime).toLocalDateTime());
                }
            }

            admission.setClassName((String) map.get("class_name"));
            Object gaokaoScore = map.get("gaokao_score");
            if (gaokaoScore != null) {
                admission.setGaokaoScore(((Number) gaokaoScore).doubleValue());
            }

            return admission;
        }).toList();

        return new PageResult<>(admissions, total, size, current);
    }

    @Override
    public boolean addAdmission(Admission admission) {
        admission.setCreateTime(LocalDateTime.now());
        admission.setUpdateTime(LocalDateTime.now());
        return save(admission);
    }

    @Override
    public boolean updateAdmission(Admission admission) {
        admission.setUpdateTime(LocalDateTime.now());
        return updateById(admission);
    }

    @Override
    public boolean deleteAdmission(Long id) {
        return removeById(id);
    }

    @Override
    public List<Admission> searchAdmissions(String batch, String universityName) {
        LambdaQueryWrapper<Admission> queryWrapper = new LambdaQueryWrapper<>();

        if (batch != null && !batch.isEmpty()) {
            queryWrapper.eq(Admission::getAdmissionBatch, batch);
        }

        if (universityName != null && !universityName.isEmpty()) {
            queryWrapper.like(Admission::getUniversityName, universityName);
        }

        queryWrapper.orderByDesc(Admission::getAdmissionTime);

        return list(queryWrapper);
    }

    @Override
    public List<Admission> getBatchStudents() {
        return baseMapper.getBatchStudents();
    }
}