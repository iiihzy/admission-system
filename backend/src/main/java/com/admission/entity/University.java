package com.admission.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("university")
public class University {
    @TableId(value = "university_id", type = IdType.INPUT)
    private String universityId;

    private String universityName;

    private String address;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}