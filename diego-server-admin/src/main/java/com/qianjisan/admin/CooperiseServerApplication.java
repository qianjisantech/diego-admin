package com.qianjisan.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * DCP项目管理系统 - 后端服务启动类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@SpringBootApplication
@EnableAsync
@EnableScheduling
@MapperScan(basePackages = {
        "com.qianjisan.**.mapper"
})
@ComponentScan(basePackages = {
    "com.qianjisan",
})
public class CooperiseServerApplication implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(CooperiseServerApplication.class, args);
        // ANSI颜色代码
        String reset = "\033[0m";      // 重置
        String bold = "\033[1m";       // 粗体
        String cyan = "\033[36m";       // 青色（边框）
        String brightBlue = "\033[94m"; // 亮蓝色（标题）
        // 使用彩色日志输出不同级别的信息
        System.out.println();
        System.out.println(bold + cyan + "╔═══════════════════════════════════════════════════════════╗" + reset);
        System.out.println(bold + cyan + "║" + reset + "                                                           " + bold + cyan + "║" + reset);
        System.out.println(bold + cyan + "║" + reset + "          " + bold + brightBlue + "   管理员后台管理系统    " + reset + "          " + bold + cyan + "║" + reset);
        System.out.println(bold + cyan + "║" + reset + "                                                           " + bold + cyan + "║" + reset);
        System.out.println(bold + cyan + "╚═══════════════════════════════════════════════════════════╝" + reset);
        System.out.println();
        log.info("========================================");
        log.info("DCP Admin Application Started Successfully!");
    }

    @Override
    public void run(String... args) throws Exception {
        printAllApiEndpoints();
    }

    /**
     * 打印所有API接口信息
     */
    private void printAllApiEndpoints() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                        API 接口列表                                                    ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");

        // 获取所有Controller
        Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(RestController.class);
        controllers.putAll(applicationContext.getBeansWithAnnotation(Controller.class));

        List<ApiEndpoint> endpoints = new ArrayList<>();

        for (Map.Entry<String, Object> entry : controllers.entrySet()) {
            Class<?> controllerClass = entry.getValue().getClass();
            String controllerName = getControllerName(controllerClass);

            // 获取类级别的RequestMapping
            String basePath = getBasePath(controllerClass);

            // 获取所有方法
            Method[] methods = controllerClass.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(RequestMapping.class) ||
                    method.isAnnotationPresent(GetMapping.class) ||
                    method.isAnnotationPresent(PostMapping.class) ||
                    method.isAnnotationPresent(PutMapping.class) ||
                    method.isAnnotationPresent(DeleteMapping.class) ||
                    method.isAnnotationPresent(PatchMapping.class)) {

                    ApiEndpoint endpoint = createApiEndpoint(method, basePath, controllerName);
                    if (endpoint != null) {
                        endpoints.add(endpoint);
                    }
                }
            }
        }

        // 按路径排序
        endpoints.sort((a, b) -> a.path.compareTo(b.path));

        // 打印接口信息
        for (ApiEndpoint endpoint : endpoints) {
            System.out.printf("║ %-6s │ %-60s │ %-25s ║%n",
                endpoint.method, endpoint.path, endpoint.description);
        }

        System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println("总共发现 " + endpoints.size() + " 个API接口");
        System.out.println();
    }

    /**
     * 获取控制器名称
     */
    private String getControllerName(Class<?> controllerClass) {
        String simpleName = controllerClass.getSimpleName();
        if (simpleName.endsWith("Controller")) {
            return simpleName.substring(0, simpleName.length() - 10);
        }
        return simpleName;
    }

    /**
     * 获取基础路径
     */
    private String getBasePath(Class<?> controllerClass) {
        if (controllerClass.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping requestMapping = controllerClass.getAnnotation(RequestMapping.class);
            String[] paths = requestMapping.value();
            if (paths.length > 0) {
                return paths[0];
            }
            paths = requestMapping.path();
            if (paths.length > 0) {
                return paths[0];
            }
        }
        return "";
    }

    /**
     * 创建API端点信息
     */
    private ApiEndpoint createApiEndpoint(Method method, String basePath, String controllerName) {
        String path = basePath;
        String httpMethod = "UNKNOWN";
        String description = method.getName();

        // 获取方法级别的映射
        if (method.isAnnotationPresent(GetMapping.class)) {
            GetMapping mapping = method.getAnnotation(GetMapping.class);
            path = combinePath(basePath, getFirstPath(mapping.value(), mapping.path()));
            httpMethod = "GET";
            description = getDescription(mapping, method.getName());
        } else if (method.isAnnotationPresent(PostMapping.class)) {
            PostMapping mapping = method.getAnnotation(PostMapping.class);
            path = combinePath(basePath, getFirstPath(mapping.value(), mapping.path()));
            httpMethod = "POST";
            description = getDescription(mapping, method.getName());
        } else if (method.isAnnotationPresent(PutMapping.class)) {
            PutMapping mapping = method.getAnnotation(PutMapping.class);
            path = combinePath(basePath, getFirstPath(mapping.value(), mapping.path()));
            httpMethod = "PUT";
            description = getDescription(mapping, method.getName());
        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
            DeleteMapping mapping = method.getAnnotation(DeleteMapping.class);
            path = combinePath(basePath, getFirstPath(mapping.value(), mapping.path()));
            httpMethod = "DELETE";
            description = getDescription(mapping, method.getName());
        } else if (method.isAnnotationPresent(PatchMapping.class)) {
            PatchMapping mapping = method.getAnnotation(PatchMapping.class);
            path = combinePath(basePath, getFirstPath(mapping.value(), mapping.path()));
            httpMethod = "PATCH";
            description = getDescription(mapping, method.getName());
        } else if (method.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping mapping = method.getAnnotation(RequestMapping.class);
            path = combinePath(basePath, getFirstPath(mapping.value(), mapping.path()));
            httpMethod = Arrays.stream(mapping.method())
                .map(m -> m.name())
                .collect(Collectors.joining(","));
            description = getDescription(mapping, method.getName());
        }

        return new ApiEndpoint(httpMethod, path, description);
    }

    /**
     * 合并路径
     */
    private String combinePath(String basePath, String methodPath) {
        if (basePath == null || basePath.isEmpty()) {
            return methodPath != null ? methodPath : "/";
        }
        if (methodPath == null || methodPath.isEmpty()) {
            return basePath;
        }

        String combined = basePath + (basePath.endsWith("/") ? "" : "/") + methodPath;
        return combined.startsWith("/") ? combined : "/" + combined;
    }

    /**
     * 获取第一个路径
     */
    private String getFirstPath(String[] value, String[] path) {
        if (value != null && value.length > 0) {
            return value[0];
        }
        if (path != null && path.length > 0) {
            return path[0];
        }
        return "";
    }

    /**
     * 获取描述信息
     */
    private String getDescription(Object mapping, String defaultName) {
        // 这里可以根据需要扩展，从注解或其他地方获取描述信息
        return defaultName;
    }

    /**
     * API端点信息类
     */
    static class ApiEndpoint {
        String method;
        String path;
        String description;

        ApiEndpoint(String method, String path, String description) {
            this.method = method;
            this.path = path;
            this.description = description;
        }
    }
}
