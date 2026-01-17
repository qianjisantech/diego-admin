package com.qianjisan.core.utils;

import com.qianjisan.core.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志追踪功能使用示例
 * 演示如何在代码中使用带traceid的日志
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@RestController
@RequestMapping("/example")
public class LogTraceExample {

    /**
     * 普通日志使用示例
     * 使用 @Slf4j 注解，所有日志都会自动添加traceid
     */
    @GetMapping("/normal-log")
    public Result<String> normalLogExample() {
        log.info("这是一个普通的INFO日志，会自动添加traceid");
        log.debug("这是一个DEBUG日志，包含更多详细信息");
        log.warn("这是一个WARN日志，提示潜在问题");
        log.error("这是一个ERROR日志，记录错误信息");

        // 模拟业务处理
        try {
            processBusiness();
            log.info("业务处理成功完成");
        } catch (Exception e) {
            log.error("业务处理失败", e);
        }

        return Result.success("查看控制台日志，观察traceid的显示效果");
    }

    /**
     * 高级日志使用示例
     * 使用 TraceLogger 工具类进行更灵活的日志控制
     */
    @GetMapping("/advanced-log")
    public Result<String> advancedLogExample() {
        // 使用TraceLogger包装器
        TraceLogger.TraceLoggerWrapper traceLogger = TraceLogger.wrap(log);

        String currentTraceId = TraceLogger.getTraceId();
        traceLogger.info("当前请求的traceid: {}", currentTraceId);

        // 手动设置traceid（可选）
        String customTraceId = TraceLogger.generateTraceId();
        TraceLogger.setTraceId(customTraceId);
        traceLogger.info("设置自定义traceid: {}", customTraceId);

        // 显示完整的traceid格式
        traceLogger.info("完整的traceid格式示例: {}", customTraceId);

        // 模拟嵌套调用
        callSubMethod();

        // 清理自定义traceid
        TraceLogger.clearTraceId();

        return Result.success("高级日志示例完成，traceid: " + currentTraceId);
    }

    /**
     * 子方法调用示例
     */
    private void callSubMethod() {
        log.info("进入子方法处理");
        // 模拟一些处理逻辑
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("子方法处理完成");
    }

    /**
     * 模拟业务处理
     */
    private void processBusiness() throws Exception {
        log.debug("开始执行业务逻辑");
        // 模拟业务处理
        if (Math.random() > 0.8) {
            throw new Exception("模拟业务处理异常");
        }
        log.debug("业务逻辑执行完成");
    }
}
