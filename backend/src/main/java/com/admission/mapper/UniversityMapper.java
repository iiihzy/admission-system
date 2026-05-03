package com.admission.mapper;

import com.admission.entity.University;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UniversityMapper extends BaseMapper<University> {
    List<University> searchUniversities(@Param("name") String name,
                                       @Param("address") String address);
}