# DCP Admin - 后端服务

## 项目简介

DCP项目管理系统后端服务，基于Spring Boot 3 + JDK 21 + MyBatis Plus构建。

## 技术栈

- **JDK**: 21
- **Spring Boot**: 3.2.0
- **MyBatis Plus**: 3.5.5
- **MySQL**: 8.0+
- **Redis**: 7.0+
- **Druid**: 1.2.20 (数据库连接池)
- **JWT**: 0.12.3 (身份认证)
- **Knife4j**: 4.4.0 (API文档)
- **Hutool**: 5.8.23 (工具类库)
- **FastJSON2**: 2.0.43 (JSON处理)

## 项目结构

```
dcp-admin/
├── src/main/java/com/dcp/
│   ├── DcpAdminApplication.java    # 启动类
│   ├── common/                      # 通用类
│   │   ├── Result.java             # 统一返回结果
│   │   ├── PageResult.java         # 分页结果
│   │   └── BaseEntity.java         # 基础实体类
│   ├── config/                      # 配置类
│   │   ├── SecurityConfig.java     # Security配置
│   │   ├── RedisConfig.java        # Redis配置
│   │   ├── CorsConfig.java         # 跨域配置
│   │   ├── MyBatisPlusConfig.java  # MyBatis Plus配置
│   │   └── Knife4jConfig.java      # Knife4j配置
│   ├── controller/                  # 控制器层
│   │   ├── AuthController.java     # 认证控制器
│   │   ├── UserController.java     # 用户控制器
│   │   ├── SpaceController.java    # 企业控制器
│   │   ├── WorkspaceIssueController.java  # 事项控制器
│   │   └── ...                     # 其他控制器
│   ├── service/                     # 服务层
│   │   ├── IUserService.java       # 用户服务接口
│   │   ├── impl/                   # 服务实现
│   │   └── ...                     # 其他服务
│   ├── mapper/                      # Mapper层
│   │   ├── UserMapper.java         # 用户Mapper
│   │   └── ...                     # 其他Mapper
│   ├── entity/                      # 实体类
│   │   ├── User.java               # 用户实体
│   │   ├── WorkspaceIssue.java     # 事项实体
│   │   └── ...                     # 其他实体
│   ├── dto/                         # 数据传输对象
│   ├── vo/                          # 视图对象
│   └── enums/                       # 枚举类
├── src/main/resources/
│   ├── application.yml              # 主配置文件
│   ├── application-dev.yml          # 开发环境配置
│   ├── application-prod.yml         # 生产环境配置
│   └── mapper/                      # MyBatis XML文件
└── pom.xml                          # Maven配置

```

## 快速开始

### 1. 环境要求

- JDK 21+
- Maven 3.8+
- MySQL 8.0+
- Redis 7.0+

### 2. 数据库初始化

```bash
# 导入数据库脚本
mysql -u root -p < ../dcp-vue/dcp_database.sql
```

### 3. 修改配置

编辑 `src/main/resources/application-dev.yml`，修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/dcp
    username: root
    password: your_password
```

### 4. 启动项目

```bash
# 编译
mvn clean package

# 运行
mvn spring-boot:run

# 或直接运行main方法
java -jar target/dcp-admin.jar
```

### 5. 访问服务

- **API文档**: http://localhost:8080/api/doc.html
- **Druid监控**: http://localhost:8080/api/druid/ (admin/admin)

## API接口

### 认证接口

- `POST /auth/login` - 用户登录
- `POST /auth/logout` - 用户登出
- `GET /auth/userinfo` - 获取当前用户信息
- `GET /auth/profile` - 获取用户权限信息

### 用户接口

- `GET /sysUser/list` - 获取用户列表
- `GET /sysUser/{id}` - 获取用户详情
- `POST /sysUser` - 创建用户
- `PUT /sysUser/{id}` - 更新用户
- `DELETE /sysUser/{id}` - 删除用户

### 企业接口

- `POST /space/page` - 分页查询企业列表
- `GET /space/{id}` - 获取企业详情
- `POST /space` - 创建企业
- `PUT /space/{id}` - 更新企业
- `DELETE /space/{id}` - 删除企业

### 事项接口

- `POST /workspace/issue/page` - 分页查询事项列表
- `GET /workspace/issue/{id}` - 获取事项详情
- `POST /workspace/issue` - 创建事项
- `PUT /workspace/issue/{id}` - 更新事项
- `DELETE /workspace/issue/{id}` - 删除事项

### 视图接口

- `GET /workspace/view/list` - 获取视图列表
- `GET /workspace/view/{id}` - 获取视图详情
- `POST /workspace/view` - 创建视图
- `PUT /workspace/view/{id}` - 更新视图
- `DELETE /workspace/view/{id}` - 删除视图

## 代码生成

项目使用MyBatis Plus代码生成器，可以快速生成实体类、Mapper、Service和Controller。

运行 `CodeGenerator.java` 即可生成所有代码。

## 开发规范

### 1. 接口规范

- 所有接口统一返回 `Result<T>` 类型
- 接口路径使用RESTful风格
- 使用@ApiOperation等Swagger注解描述接口

### 2. 命名规范

- 实体类：使用数据库表名对应的驼峰命名
- Mapper接口：XxxMapper
- Service接口：IXxxService
- Service实现：XxxServiceImpl
- Controller：XxxController

### 3. 异常处理

统一使用全局异常处理器处理异常

### 4. 日志规范

- 使用SLF4J + Logback
- 开发环境：DEBUG级别
- 生产环境：INFO级别

## 部署说明

### Docker部署

```bash
# 构建镜像
docker build -t dcp-admin:1.0.0 .

# 运行容器
docker run -d -p 8080:8080 --name dcp-admin dcp-admin:1.0.0
```

### jar包部署

```bash
# 打包
mvn clean package -DskipTests

# 运行
nohup java -jar target/dcp-admin.jar --spring.profiles.active=prod > logs/app.log 2>&1 &
```

## 许可证

MIT License
