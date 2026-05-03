package com.admission.service;

import com.admission.entity.University;
import com.admission.util.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IUniversityService extends IService<University> {
    PageResult<University> getUniversityPage(Page<University> page, String universityId);

    boolean addUniversity(University university);

    boolean updateUniversity(University university);

    boolean deleteUniversity(String universityId);

    List<University> searchUniversities(String name, String address);
}