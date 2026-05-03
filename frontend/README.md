# 高校学生录取信息管理系统 - 前端

这是一个基于Vue 3 + TypeScript + Element Plus的现代化高校学生录取信息管理系统前端。

## 技术栈

- **框架**: Vue 3.3+ (Composition API)
- **语言**: TypeScript
- **构建工具**: Vite
- **UI组件库**: Element Plus
- **状态管理**: Pinia
- **路由**: Vue Router 4
- **HTTP客户端**: Axios
- **图表库**: ECharts
- **自动导入**: unplugin-auto-import, unplugin-vue-components

## 项目结构

```
src/
├── api/                 # API接口
├── assets/              # 静态资源
├── components/          # 公共组件
│   ├── common/          # 通用组件
│   └── layout/          # 布局组件
├── router/              # 路由配置
├── store/               # 状态管理
├── utils/               # 工具函数
├── views/               # 页面视图
│   ├── dashboard/       # 首页
│   ├── student/         # 学生管理
│   ├── university/      # 院校管理
│   ├── admission/       # 录取管理
│   ├── statistics/      # 统计分析
│   └── login/           # 登录页
├── App.vue              # 根组件
└── main.js              # 入口文件
```

## 安装依赖

```bash
npm install
```

## 开发运行

```bash
npm run dev
```

项目将在 http://localhost:3000 上运行

## 构建生产版本

```bash
npm run build
```

## 功能特性

1. **用户认证** - 登录/登出功能
2. **学生管理** - 增删改查学生信息
3. **院校管理** - 增删改查院校信息
4. **录取管理** - 增删改查录取信息
5. **统计分析** - 班级批次统计和学生视图
6. **响应式设计** - 适配不同屏幕尺寸

## API代理配置

开发环境下，API请求会通过Vite代理转发到后端服务器：
- 请求 `/api/xxx` 会被代理到 `http://localhost:8080/api/xxx`

## 注意事项

1. 确保后端服务已在运行（默认端口8080）
2. 所有API请求都会自动携带JWT token
3. 页面访问需要登录认证