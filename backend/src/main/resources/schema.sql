-- 创建数据库
CREATE DATABASE IF NOT EXISTS admission_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE admission_system;

-- 学生基本信息表
CREATE TABLE student (
    exam_number VARCHAR(20) PRIMARY KEY COMMENT '考号',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    gender ENUM('男', '女') NOT NULL COMMENT '性别',
    birth_date DATE COMMENT '出生年月',
    class_name VARCHAR(50) NOT NULL COMMENT '班级',
    native_place VARCHAR(100) COMMENT '籍贯',
    gaokao_score DECIMAL(6,2) NOT NULL COMMENT '高考成绩',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生基本信息表';

-- 录取院校信息表
CREATE TABLE university (
    university_id VARCHAR(20) PRIMARY KEY COMMENT '院校编号',
    university_name VARCHAR(100) NOT NULL COMMENT '院校名称',
    address VARCHAR(200) COMMENT '院校地址',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='录取院校信息表';

-- 学生录取信息表
CREATE TABLE admission (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '自增ID',
    exam_number VARCHAR(20) NOT NULL COMMENT '考号',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    university_id VARCHAR(20) NOT NULL COMMENT '录取院校编号',
    university_name VARCHAR(100) NOT NULL COMMENT '录取院校名称',
    admission_batch ENUM('提前批', '本科一批', '本科二批', '专科批', '其他') NOT NULL COMMENT '录取批次',
    admission_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '录取时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (exam_number) REFERENCES student(exam_number) ON DELETE CASCADE,
    FOREIGN KEY (university_id) REFERENCES university(university_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生录取信息表';

-- 创建索引
CREATE INDEX idx_student_class_name ON student(class_name);
CREATE INDEX idx_student_gaokao_score ON student(gaokao_score);
CREATE INDEX idx_university_name ON university(university_name);
CREATE INDEX idx_admission_batch ON admission(admission_batch);

-- 存储过程：统计各班级不同批次录取的人数
DELIMITER //
CREATE PROCEDURE count_admission_by_class_and_batch()
BEGIN
    SELECT
        s.class_name AS '班级',
        a.admission_batch AS '录取批次',
        COUNT(*) AS '录取人数'
    FROM
        admission a
    JOIN
        student s ON a.exam_number = s.exam_number
    GROUP BY
        s.class_name, a.admission_batch
    ORDER BY
        s.class_name, FIELD(a.admission_batch, '提前批', '本科一批', '本科二批', '专科批', '其他');
END //
DELIMITER ;

-- 视图：查询被不同批次院校录取的学生信息
CREATE VIEW view_batch_admission_students AS
SELECT
    s.exam_number AS '考号',
    s.name AS '姓名',
    s.gender AS '性别',
    s.class_name AS '班级',
    a.university_name AS '录取院校',
    s.gaokao_score AS '高考成绩',
    a.admission_batch AS '录取批次'
FROM
    admission a
JOIN
    student s ON a.exam_number = s.exam_number
ORDER BY
    a.admission_batch, s.class_name, s.gaokao_score DESC;

-- 触发器：删除院校时级联删除学生录取信息
DELIMITER //
CREATE TRIGGER trigger_delete_university_admission
AFTER DELETE ON university
FOR EACH ROW
BEGIN
    DELETE FROM admission WHERE university_id = OLD.university_id;
END //
DELIMITER ;

-- 插入测试数据
INSERT INTO student (exam_number, name, gender, birth_date, class_name, native_place, gaokao_score) VALUES
('20240001', '张三', '男', '2006-05-15', '高三(1)班', '北京市', 680.50),
('20240002', '李四', '女', '2006-03-22', '高三(1)班', '上海市', 675.00),
('20240003', '王五', '男', '2006-08-10', '高三(2)班', '广州市', 690.00),
('20240004', '赵六', '女', '2006-01-18', '高三(2)班', '深圳市', 685.50),
('20240005', '钱七', '男', '2006-11-25', '高三(3)班', '杭州市', 678.00);

INSERT INTO university (university_id, university_name, address) VALUES
('1001', '清华大学', '北京市海淀区清华园1号'),
('1002', '北京大学', '北京市海淀区颐和园路5号'),
('1003', '复旦大学', '上海市杨浦区邯郸路220号'),
('1004', '上海交通大学', '上海市闵行区东川路800号'),
('1005', '浙江大学', '浙江省杭州市西湖区余杭塘路866号');

INSERT INTO admission (exam_number, name, university_id, university_name, admission_batch) VALUES
('20240001', '张三', '1001', '清华大学', '本科一批'),
('20240002', '李四', '1002', '北京大学', '本科一批'),
('20240003', '王五', '1003', '复旦大学', '本科一批'),
('20240004', '赵六', '1004', '上海交通大学', '本科一批'),
('20240005', '钱七', '1005', '浙江大学', '本科一批');