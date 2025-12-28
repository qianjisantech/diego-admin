package com.qianjisan.admin.startup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 在应用启动完成后打印各模块加载情况（检测每个模块是否有加载的 Bean）
 */
@Slf4j
@Component
public class ModuleStartupLogger implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private ApplicationContext applicationContext;

    private static final Map<String, String> MODULE_PACKAGE_PREFIXES = createModuleMap();

    private static Map<String, String> createModuleMap() {
        Map<String, String> map = new LinkedHashMap<>();
        // 这里列出工程中主要模块对应的包名前缀，用于检测是否加载
        map.put("admin", "com.qianjisan.admin");
        map.put("core", "com.qianjisan.core");
        map.put("common", "com.qianjisan.common");
        map.put("console", "com.qianjisan.console");
        map.put("system", "com.qianjisan.system");
        map.put("issue", "com.qianjisan.issue");
        map.put("auth", "qianjisan.auth");
        map.put("view", "com.qianjisan.view");
        map.put("cs", "com.qianjisan.cs");
        return map;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n--------------------------------------------------\n");
        sb.append("模块加载情况：\n");

        String[] beanNames = applicationContext.getBeanDefinitionNames();

        Map<String, Integer> moduleCounts = new LinkedHashMap<>();
        for (String module : MODULE_PACKAGE_PREFIXES.keySet()) {
            moduleCounts.put(module, 0);
        }

        for (String beanName : beanNames) {
            Class<?> type = null;
            try {
                type = applicationContext.getType(beanName);
            } catch (Throwable ignored) {
            }
            if (type == null) continue;
            String className = type.getName();
            for (Map.Entry<String, String> entry : MODULE_PACKAGE_PREFIXES.entrySet()) {
                if (className.startsWith(entry.getValue())) {
                    moduleCounts.put(entry.getKey(), moduleCounts.get(entry.getKey()) + 1);
                    break;
                }
            }
        }

        for (Map.Entry<String, Integer> entry : moduleCounts.entrySet()) {
            if (entry.getValue() > 0) {
                String msg = String.format("模块 [%s] 加载成功，Bean 数: %d", entry.getKey(), entry.getValue());
                sb.append(msg).append("\n");
                log.info(msg);
            } else {
                String msg = String.format("模块 [%s] 未检测到加载的 Bean", entry.getKey());
                sb.append(msg).append("\n");
                log.warn(msg);
            }
        }

        sb.append("--------------------------------------------------\n");
        System.out.println(sb.toString());
    }
}