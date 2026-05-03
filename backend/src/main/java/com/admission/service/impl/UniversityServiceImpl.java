package com.admission.service.impl;

import com.admission.entity.University;
import com.admission.mapper.UniversityMapper;
import com.admission.service.IUniversityService;
import com.admission.util.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniversityServiceImpl extends ServiceImpl<UniversityMapper, University> implements IUniversityService {

    @Override
    public PageResult<University> getUniversityPage(Page<University> page, String universityId) {
        LambdaQueryWrapper<University> queryWrapper = new LambdaQueryWrapper<>();

        // 如果传了 universityId，就作为查询条件
        if (universityId != null && !universityId.isEmpty()) {
            queryWrapper.eq(University::getUniversityId, universityId);
        }

        // 传入 queryWrapper 执行查询
        Page<University> result = page(page, queryWrapper);
        return new PageResult<>(result.getRecords(), result.getTotal(), result.getSize(), result.getCurrent());
    }

    @Override
    public boolean addUniversity(University university) {
        return save(university);
    }

    @Override
    public boolean updateUniversity(University university) {
        return updateById(university);
    }

    @Override
    public boolean deleteUniversity(String universityId) {
        return removeById(universityId);
    }

    @Override
    public List<University> searchUniversities(String name, String address) {
        LambdaQueryWrapper<University> queryWrapper = new LambdaQueryWrapper<>();

        if (name != null && !name.isEmpty()) {
            queryWrapper.like(University::getUniversityName, name);
        }

        if (address != null && !address.isEmpty()) {
            queryWrapper.like(University::getAddress, address);
        }

        queryWrapper.orderByDesc(University::getCreateTime);

        return list(queryWrapper);
    }
}