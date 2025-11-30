package com.dcp.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcp.common.Result;
import com.dcp.common.annotation.RequiresPermission;
import com.dcp.common.dto.OperationLogQueryDTO;
import com.dcp.common.vo.OperationLogVO;
import com.dcp.common.vo.PageVO;
import com.dcp.entity.SysOperationLog;
import com.dcp.service.IOperationLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 操作日志控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "操作日志管理", description = "操作日志相关接口")
@RestController
@RequestMapping("/operation-log")
@RequiredArgsConstructor
public class OperationLogController {

    private final IOperationLogService operationLogService;

    @Operation(summary = "分页查询操作日志")
    @RequiresPermission("record:operation:view")
    @PostMapping("/page")
    public Result<PageVO<OperationLogVO>> page(@RequestBody OperationLogQueryDTO query) {
        Page<OperationLogVO> page = operationLogService.pageQuery(query);
        return Result.success(PageVO.of(page));
    }

    @Operation(summary = "根据ID查询操作日志")
    @RequiresPermission("record:operation:view")
    @GetMapping("/{id}")
    public Result<OperationLogVO> getById(@PathVariable Long id) {
        SysOperationLog log = operationLogService.getById(id);
        OperationLogVO vo = new OperationLogVO();
        if (log != null) {
            org.springframework.beans.BeanUtils.copyProperties(log, vo);
        }
        return Result.success(vo);
    }
}
