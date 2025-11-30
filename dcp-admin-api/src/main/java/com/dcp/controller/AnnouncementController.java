package com.dcp.controller;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcp.common.Result;
import com.dcp.common.annotation.RequiresPermission;
import com.dcp.common.context.UserContextHolder;
import com.dcp.common.request.AnnouncementQueryRequest;
import com.dcp.common.request.AnnouncementRequest;
import com.dcp.common.vo.AnnouncementVO;
import com.dcp.entity.Announcement;
import com.dcp.service.IAnnouncementService;
import com.dcp.service.IAnnouncementUserRelationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公告管理控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "公告管理", description = "Announcement相关接口")
@RestController
@RequestMapping("/announcement")
@RequiredArgsConstructor
@Slf4j
public class AnnouncementController {

    private final IAnnouncementService announcementService;

    @Operation(summary = "创建公告管理")
    @RequiresPermission("announcement:add")
    @PostMapping
    public Result<Void> create(@RequestBody AnnouncementRequest request) {
        try {
            announcementService.saveAnnouncement(request);
            return Result.success();
        }catch (Exception e){
            log.error("创建公告失败，失败原因：{}",e.getMessage());
            return Result.error(e.getMessage());
        }

    }

    @Operation(summary = "更新公告管理")
    @RequiresPermission("announcement:edit")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody AnnouncementRequest request) {
      try {
          announcementService.updateAnnouncementById(id,request);
          return Result.success();
      }catch (Exception e){
          log.error("更新公告失败,失败原因：{}",e.getMessage());
          return Result.error(e.getMessage());
      }

    }

    @Operation(summary = "删除公告管理")
    @RequiresPermission("announcement:delete")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
     try {
         announcementService.removeById(id);
         return Result.success();
     }catch (Exception e){
         log.error("删除公告失败，失败原因：{}",e.getMessage());
         return Result.error(e.getMessage());
     }
    }

    @Operation(summary = "查询公告管理列表")
    @RequiresPermission("announcement:view")
    @GetMapping("/list")
    public Result<List<Announcement>> list() {
        List<Announcement> list = announcementService.list();
        return Result.success(list);
    }

    @Operation(summary = "分页查询公告管理")
    @RequiresPermission("announcement:view")
    @PostMapping("/page")
    public Result<Page<AnnouncementVO>> page(@RequestBody AnnouncementQueryRequest request) {
        log.info("分页查询公告管理入参:{}", JSON.toJSONString(request));
        try {
            Page<AnnouncementVO> page = announcementService.queryPage(request);
            return Result.success(page);
        } catch (Exception e) {
            log.error("分页查询公告管理失败：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "标记公告为已读")
    @PostMapping("/mark-read/{announcementId}")
    public Result<Void> markAnnouncementAsRead(@PathVariable Long announcementId) {
        try {
            Long userId = UserContextHolder.getUserId();
            announcementService.markAnnouncementAsRead(userId, announcementId);
            return Result.success();
        } catch (Exception e) {
            log.error("标记公告为已读失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }
}
