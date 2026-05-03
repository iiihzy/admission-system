package com.admission.service;

import com.admission.entity.Admission;
import com.admission.util.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IAdmissionService extends IService<Admission> {
    PageResult<Admission> getAdmissionPage(Page<Admission> page);

    PageResult<Admission> getAdmissionPageWithStudentInfo(Long current, Long size);

    boolean addAdmission(Admission admission);

    boolean updateAdmission(Admission admission);

    boolean deleteAdmission(Long id);

    List<Admission> searchAdmissions(String batch, String universityName);

    List<Admission> getBatchStudents();
}