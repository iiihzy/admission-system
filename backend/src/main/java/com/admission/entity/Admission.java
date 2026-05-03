package com.admission.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat; // ✅ 1. 引入 Jackson 注解
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("admission")
public class Admission {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String examNumber;

    private String name;

    private String universityId;

    private String universityName;

    private String admissionBatch;

    // ✅ 2. 添加注解：指定前端传入的时间格式，并设置时区防止偏差
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime admissionTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    // 视图中的额外字段
    private String className;

    private Double gaokaoScore;
}