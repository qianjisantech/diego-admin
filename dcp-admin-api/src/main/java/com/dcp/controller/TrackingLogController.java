package com.dcp.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcp.common.Result;
import com.dcp.common.annotation.RequiresPermission;
import com.dcp.common.dto.TrackingLogQueryDTO;
import com.dcp.common.request.TrackingLogRequest;
import com.dcp.common.vo.PageVO;
import com.dcp.common.vo.TrackingLogVO;
import com.dcp.service.ITrackingLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    private final ITrackingLogService trackingLogService;

    @Operation(summary = "分页查询埋点日志")
    @RequiresPermission("record:tracking:view")
    @PostMapping("/page")
    public Result<PageVO<TrackingLogVO>> page(@RequestBody TrackingLogQueryDTO query) {
        log.info("[分页查询埋点日志] 查询参数: {}", query);
        Page<TrackingLogVO> page = trackingLogService.pageQuery(query);
        return Result.success(PageVO.of(page));
    }

    @Operation(summary = "保存埋点日志")
    @PostMapping("/save")
    public Result<Void> save(@RequestBody TrackingLogRequest request) {
        log.info("[保存埋点日志] 请求参数: {}", request);
        trackingLogService.saveTrackingLog(request);
        return Result.success();
    }

    @Operation(summary = "查询埋点日志详情")
    @RequiresPermission("record:tracking:view")
    @GetMapping("/{id}")
    public Result<TrackingLogVO> getById(@PathVariable Long id) {
        log.info("[查询埋点日志详情] ID: {}", id);
        TrackingLogVO vo = trackingLogService.getTrackingLogById(id);
        return Result.success(vo);
    }
}
