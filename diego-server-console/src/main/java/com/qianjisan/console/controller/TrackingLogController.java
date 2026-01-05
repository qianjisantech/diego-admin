package com.qianjisan.console.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianjisan.annotation.RequiresPermission;
import com.qianjisan.core.PageVO;
import com.qianjisan.core.Result;
import com.qianjisan.system.request.SysTrackingLogRequest;
import com.qianjisan.system.request.SysTrackingLogQueryRequest;
import com.qianjisan.system.request.TrackingReportQueryRequest;
import com.qianjisan.system.service.ISysTrackingLogService;
import com.qianjisan.system.vo.EventTypeStatisticsVO;
import com.qianjisan.system.vo.SysTrackingLogVO;
import com.qianjisan.system.vo.UserActivityVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * 埋点日志控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "埋点管理", description = "埋点相关接口")
@RestController
@RequestMapping("/tracking")
@RequiredArgsConstructor
@Slf4j
public class TrackingLogController {

    private final ISysTrackingLogService trackingLogService;

    @Operation(summary = "分页查询埋点日志")
    @PostMapping("/page")
    public Result<PageVO<SysTrackingLogVO>> page(@RequestBody SysTrackingLogQueryRequest request) {
        log.info("[分页查询埋点日志] 查询参数: {}", request);
        Page<SysTrackingLogVO> page = trackingLogService.pageQuery(request);
        return Result.success(PageVO.of(page));
    }

    @Operation(summary = "保存埋点日志")
    @PostMapping("/save")
    public Result<Void> save(@RequestBody SysTrackingLogRequest request) {
        log.info("[保存埋点日志] 请求参数: {}", request);
        trackingLogService.saveTrackingLog(request);
        return Result.success();
    }

    @Operation(summary = "查询埋点日志详情")
    @GetMapping("/{id}")
    public Result<SysTrackingLogVO> getById(@PathVariable Long id) {
        log.info("[查询埋点日志详情] ID: {}", id);
        SysTrackingLogVO vo = trackingLogService.getTrackingLogById(id);
        return Result.success(vo);
    }

    @Operation(summary = "统计埋点类型数量（柱状图）", description = "展示年月日每个埋点类型的量")
    @PostMapping("/report/event-type-statistics")
    public Result<List<EventTypeStatisticsVO>> eventTypeStatistics(@RequestBody TrackingReportQueryRequest request) {
        log.info("[统计埋点类型数量] 查询参数: {}", request);
        List<EventTypeStatisticsVO> result = trackingLogService.statisticsByEventType(request);
        return Result.success(result);
    }

    @Operation(summary = "统计用户活跃量", description = "展示用户的活跃量")
    @PostMapping("/report/user-activity")
    public Result<List<UserActivityVO>> userActivity(@RequestBody SysTrackingLogQueryRequest request) {
        log.info("[统计用户活跃量] 查询参数: {}", request);
        List<UserActivityVO> result = trackingLogService.statisticsUserActivity(request);
        return Result.success(result);
    }

    @Operation(summary = "埋点报表（整合）", description = "返回事件类型统计与用户活跃报表")
    @PostMapping("/report")
    public Result<Map<String, Object>> report(@RequestBody TrackingReportQueryRequest request) {
        log.info("[埋点报表] 查询参数: {}", request);
        // 事件类型统计
        List<EventTypeStatisticsVO> eventStats = trackingLogService.statisticsByEventType(request);
        // 用户活跃量统计 - 转换为 SysTrackingLogQueryRequest（复用时间范围与 timeType）
        com.qianjisan.system.request.SysTrackingLogQueryRequest q = new com.qianjisan.system.request.SysTrackingLogQueryRequest();
        q.setStartTime(request.getStartTime());
        q.setEndTime(request.getEndTime());
        q.setTimeType(request.getTimeType());
        List<UserActivityVO> userActivity = trackingLogService.statisticsUserActivity(q);

        Map<String, Object> resp = new HashMap<>();
        resp.put("eventTypeStatistics", eventStats);
        resp.put("userActivity", userActivity);
        return Result.success(resp);
    }
}
