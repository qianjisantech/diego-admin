# ================================
# DCP Admin 项目 Dockerfile
# ================================
# 多阶段构建，减小最终镜像体积
# 支持多架构: linux/amd64 和 linux/arm64
# Stage 1: 构建阶段（使用 Maven 构建）
# Stage 2: 运行阶段（使用 Alpine + JRE 21）

# ================================
# Stage 1: 构建阶段
# ================================
# 选择构建用基础镜像，使用 Maven 3.9.5 + JDK 21（支持多架构）
FROM --platform=$BUILDPLATFORM maven:3.9.5-eclipse-temurin-21 AS build

# 指定构建过程中的工作目录
WORKDIR /app

# ====== 设置 JAVA_HOME 环境变量（修复 Maven 构建问题）======
ENV JAVA_HOME=/opt/java/openjdk
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# 将主 POM 文件拷贝到工作目录（不再使用 settings.xml）
COPY pom.xml /app/

# 将各模块的 pom.xml 拷贝到工作目录（按照主pom.xml中的modules顺序）
COPY cooperise-server-core/pom.xml         /app/cooperise-server-core/
COPY cooperise-server-common/pom.xml       /app/cooperise-server-common/
COPY cooperise-server-system/pom.xml       /app/cooperise-server-system/
COPY cooperise-server-enterprise/pom.xml   /app/cooperise-server-enterprise/
COPY cooperise-server-auth/pom.xml         /app/cooperise-server-auth/
COPY cooperise-server-console/pom.xml      /app/cooperise-server-console/
COPY cooperise-server-admin/pom.xml        /app/cooperise-server-admin/
# 下载依赖（利用 Docker 缓存层，只有 pom.xml 变化时才重新下载）
# 使用 Maven 中央仓库下载依赖
# 定义revision属性以解决版本变量解析问题
# 即使 dependency:go-offline 失败也继续，因为 package 阶段会重新下载依赖
RUN mvn -f /app/pom.xml dependency:go-offline -B -Drevision=1.0.0-release || true

# 将 src 目录下所有文件，拷贝到工作目录中（.dockerignore 中文件除外）
COPY cooperise-server-core/src        /app/cooperise-server-core/src
COPY cooperise-server-common/src      /app/cooperise-server-common/src
COPY cooperise-server-system/src      /app/cooperise-server-system/src
COPY cooperise-server-enterprise/src  /app/cooperise-server-enterprise/src
COPY cooperise-server-auth/src        /app/cooperise-server-auth/src
COPY cooperise-server-console/src     /app/cooperise-server-console/src
COPY cooperise-server-admin/src       /app/cooperise-server-admin/src


RUN mvn -f /app/pom.xml clean install -DskipTests -N -B -Drevision=1.0.0-release

RUN mvn -f /app/pom.xml clean install -DskipTests -B -Drevision=1.0.0-release -am

FROM eclipse-temurin:21-jre-alpine

RUN apk add --no-cache tzdata curl

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 指定运行时的工作目录
WORKDIR /app


RUN addgroup -S appgroup && adduser -S appuser -G appgroup


COPY --from=build /app/cooperise-server-admin/target/*.jar /app/app.jar

# 创建日志和上传目录
RUN mkdir -p /app/logs /app/uploads && \
    chown -R appuser:appgroup /app


EXPOSE 80

# JVM 参数配置
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC -XX:MaxGCPauseMillis=200"


CMD ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar --spring.profiles.active=prod"]