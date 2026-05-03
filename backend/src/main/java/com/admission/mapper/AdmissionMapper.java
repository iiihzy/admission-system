package com.admission.mapper;

import com.admission.entity.Admission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdmissionMapper extends BaseMapper<Admission> {
    List<Admission> searchAdmissions(@Param("batch") String batch,
                                    @Param("universityName") String universityName);

    List<Admission> getBatchStudents();
}