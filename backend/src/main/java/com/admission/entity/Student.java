package com.admission.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("student")
public class Student {
    @TableId(value = "exam_number", type = IdType.INPUT)
    private String examNumber;

    private String name;

    private String gender;

    private LocalDate birthDate;

    private String className;

    private String nativePlace;

    private BigDecimal gaokaoScore;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}