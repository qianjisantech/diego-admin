# ================================
# DCP Admin 项目 Dockerfile
# ================================
# 多阶段构建，减小最终镜像体积
# Stage 1: 构建阶段（使用 Maven 构建）
# Stage 2: 运行阶段（使用 Alpine + JRE 21）

# ================================
# Stage 1: 构建阶段
# ================================
# 选择构建用基础镜像，使用 Maven 3.9.5 + JDK 21
FROM maven:3.9.5-eclipse-temurin-21 AS build

# 指定构建过程中的工作目录
WORKDIR /app

# ====== 设置 JAVA_HOME 环境变量（修复 Maven 构建问题）======
ENV JAVA_HOME=/opt/java/openjdk
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# 将主 POM 文件拷贝到工作目录（不再使用 settings.xml）
COPY pom.xml /app/

# 将各模块的 pom.xml 拷贝到工作目录
COPY dcp-admin-common/pom.xml /app/dcp-admin-common/
COPY dcp-admin-dao/pom.xml /app/dcp-admin-dao/
COPY dcp-admin-manager/pom.xml /app/dcp-admin-manager/
COPY dcp-admin-core/pom.xml /app/dcp-admin-core/
COPY dcp-admin-api/pom.xml /app/dcp-admin-api/
COPY dcp-admin-rbac/pom.xml /app/dcp-admin-rbac/

# 下载依赖（利用 Docker 缓存层，只有 pom.xml 变化时才重新下载）
# 使用 Maven 中央仓库下载依赖
# 即使 dependency:go-offline 失败也继续，因为 package 阶段会重新下载依赖
RUN mvn -f /app/pom.xml dependency:go-offline -B || true

# 将 src 目录下所有文件，拷贝到工作目录中（.dockerignore 中文件除外）
COPY dcp-admin-common/src /app/dcp-admin-common/src
COPY dcp-admin-dao/src /app/dcp-admin-dao/src
COPY dcp-admin-manager/src /app/dcp-admin-manager/src
COPY dcp-admin-core/src /app/dcp-admin-core/src
COPY dcp-admin-api/src /app/dcp-admin-api/src
COPY dcp-admin-rbac/src /app/dcp-admin-rbac/src

# 执行代码编译命令，跳过测试以加快构建速度
RUN mvn -f /app/pom.xml clean package -DskipTests -B

# ================================
# Stage 2: 运行阶段
# ================================
# 选择运行时基础镜像，使用 Eclipse Temurin JRE 21 官方镜像
FROM eclipse-temurin:21-jre-alpine

# 安装必要的工具（curl 用于健康检查）
RUN apk add --no-cache tzdata curl

# 设置时区为上海时间
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 指定运行时的工作目录
WORKDIR /app

# 创建非 root 用户（安全最佳实践）
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# 将构建产物 jar 包从构建阶段拷贝到运行时目录
COPY --from=build /app/dcp-admin-api/target/*.jar /app/app.jar

# 创建日志和上传目录
RUN mkdir -p /app/logs /app/uploads && \
    chown -R appuser:appgroup /app

# 注意：由于应用需要绑定 80 端口（特权端口），必须以 root 用户运行
# 在容器环境中，以 root 运行是可以接受的，因为容器本身提供了隔离
# USER appuser  # 暂时注释掉，保持 root 用户运行

# 暴露端口
# 此处端口必须与「服务设置」-「流水线」以及「手动上传代码包」部署时填写的端口一致，否则会部署失败。
# 应用运行在 80 端口（参见 application.yml）
EXPOSE 80

# JVM 参数配置
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC -XX:MaxGCPauseMillis=200"

# 健康检查
# 使用自定义的健康检查接口，初始等待 40 秒以便应用启动
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
    CMD curl -f http://localhost:80/api/health || exit 1

# 执行启动命令
# 写多行独立的 CMD 命令是错误写法！只有最后一行 CMD 命令会被执行，之前的都会被忽略，导致业务报错。
# 请参考 Docker 官方文档之 CMD 命令：https://docs.docker.com/engine/reference/builder/#cmd
CMD ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar"]
