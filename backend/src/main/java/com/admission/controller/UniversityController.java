package com.admission.controller;

import com.admission.entity.University;
import com.admission.service.IUniversityService;
import com.admission.util.PageResult;
import com.admission.util.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/universities")
@CrossOrigin(origins = "http://localhost:3000")
public class UniversityController {

    @Autowired
    private IUniversityService universityService;

    // ✅ 新增：获取单个院校详情
    @GetMapping("/{universityId}")
    public Result<University> getUniversityById(@PathVariable String universityId) {
        try {
            University university = universityService.getById(universityId);
            if (university != null) {
                return Result.success(university);
            } else {
                return Result.error("院校不存在");
            }
        } catch (Exception e) {
            return Result.error("获取院校详情失败: " + e.getMessage());
        }
    }


    @PostMapping
    public Result<Boolean> addUniversity(@RequestBody University university) {
        try {
            boolean success = universityService.addUniversity(university);
            return success ? Result.success(true) : Result.error("添加院校失败");
        } catch (Exception e) {
            return Result.error("添加院校失败: " + e.getMessage());
        }
    }

    @PutMapping("/{universityId}")
    public Result<Boolean> updateUniversity(@PathVariable String universityId, @RequestBody University university) {
        try {
            university.setUniversityId(universityId);
            boolean success = universityService.updateUniversity(university);
            return success ? Result.success(true) : Result.error("更新院校失败");
        } catch (Exception e) {
            return Result.error("更新院校失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/{universityId}")
    public Result<Boolean> deleteUniversity(@PathVariable String universityId) {
        try {
            boolean success = universityService.deleteUniversity(universityId);
            return success ? Result.success(true) : Result.error("删除院校失败");
        } catch (Exception e) {
            return Result.error("删除院校失败: " + e.getMessage());
        }
    }

    @GetMapping
    public Result<PageResult<University>> getUniversities(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) String universityId // ✅ 新增这一行
    ) {
        try {
            Page<University> page = new Page<>(current, size);
            PageResult<University> pageResult = universityService.getUniversityPage(page, universityId); // ✅ 传入 ID
            return Result.success(pageResult);
        } catch (Exception e) {
            return Result.error("获取院校列表失败: " + e.getMessage());
        }
    }
}