# 高校学生录取信息管理系统

## 项目概述
这是一个基于Spring Boot 3.2.0 + Vue 3 + Naive UI开发的高校学生录取信息管理系统，实现了学生基本信息、录取院校信息、学生录取信息的全面管理。

## 技术栈
- **后端**: Spring Boot 3.2.0, MyBatis-Plus, MySQL 8.0, JWT
- **前端**: Vue 3, Naive UI, Vue Router, Pinia, Axios
- **数据库**: MySQL 8.0
- **构建工具**: Maven, Node.js

## 项目结构
```
admission-system/
├── backend/                 # 后端项目
│   ├── src/main/java/com/admission/
│   │   ├── config/         # 配置类
│   │   ├── controller/     # 控制器
│   │   ├── entity/         # 实体类
│   │   ├── mapper/         # Mapper接口
│   │   ├── service/        # 服务层
│   │   └── util/           # 工具类
│   └── src/main/resources/
│       ├── application.yml # 配置文件
│       └── schema.sql      # 数据库脚本
└── frontend/               # 前端项目
    ├── src/
    │   ├── api/            # API接口
    │   ├── assets/         # 静态资源
    │   ├── components/     # 公共组件
    │   ├── router/         # 路由配置
    │   ├── store/          # 状态管理
    │   ├── utils/          # 工具函数
    │   └── views/          # 页面视图
    └── vue.config.js       # Vue配置
```

## 功能特性
1. **学生基本信息管理**
   - 添加、编辑、删除学生信息
   - 按姓名、班级、成绩范围搜索

2. **录取院校信息管理**
   - 添加、编辑、删除院校信息
   - 按名称、地址搜索

3. **学生录取信息管理**
   - 添加、编辑、删除录取信息
   - 按批次、院校搜索
   - 关联学生和院校信息

4. **统计分析功能**
   - 班级批次统计（调用存储过程）
   - 批次学生视图查询
   - 图表展示

5. **系统管理**
   - 用户登录/注销
   - JWT认证

## 快速开始

### 1. 环境要求
- JDK 17+
- Node.js 16+
- MySQL 8.0+

### 2. 数据库配置
1. 创建数据库：
```sql
CREATE DATABASE admission_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 执行数据库脚本：
```bash
# 在backend/src/main/resources/schema.sql中已包含完整的初始化脚本
# 在MySQL中执行该脚本即可
```

### 3. 后端启动
1. 进入后端目录：
```bash
cd backend
```

2. 安装依赖：
```bash
mvn clean install
```

3. 运行项目：
```bash
mvn spring-boot:run
```
或打包运行：
```bash
mvn clean package
java -jar target/admission-system-1.0.0.jar
```

后端服务将在 http://localhost:8080 启动

### 4. 前端启动
1. 进入前端目录：
```bash
cd frontend
```

2. 安装依赖：
```bash
npm install
```

3. 启动开发服务器：
```bash
npm run serve
```

前端应用将在 http://localhost:8081 启动

### 5. 访问系统
打开浏览器访问 http://localhost:8081

### 6. 登录信息
- 用户名：admin
- 密码：admin123

## API接口
- 后端基础路径：/api
- 认证方式：JWT Bearer Token

### 主要接口
- 学生管理：/api/students
- 院校管理：/api/universities
- 录取管理：/api/admissions
- 统计分析：/api/statistics
- 认证：/api/auth

## 数据库设计
系统包含3个主要表：
1. student - 学生基本信息表
2. university - 录取院校信息表
3. admission - 学生录取信息表

还包括存储过程、视图和触发器。

## 开发说明
- 后端使用Spring Boot 3.x和MyBatis-Plus
- 前端使用Vue 3 Composition API和Naive UI
- 使用JWT进行身份认证
- 支持跨域请求
- 响应式设计，适配不同屏幕

## 注意事项
1. 确保MySQL服务已启动
2. 数据库配置信息在application.yml中
3. 前端代理配置在vue.config.js中
4. 默认端口：后端8080，前端8081

## 许可证
MIT License