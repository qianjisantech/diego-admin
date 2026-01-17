package com.qianjisan.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianjisan.system.entity.SysTrackingLog;
import com.qianjisan.system.request.SysTrackingLogQueryRequest;
import com.qianjisan.system.request.SysTrackingLogRequest;
import com.qianjisan.system.request.TrackingReportQueryRequest;
import com.qianjisan.system.vo.EventTypeStatisticsVO;
import com.qianjisan.system.vo.SysTrackingLogVO;
import com.qianjisan.system.vo.UserActivityVO;

import java.util.List;

/**
 * 埋点日志服务接口
 */
public interface ISysTrackingLogService extends IService<SysTrackingLog> {

    /**
     * 分页查询埋点日志
     *
     * @param request 查询条件
     * @return 分页结果
     */
    Page<SysTrackingLogVO> pageQuery(SysTrackingLogQueryRequest request);

    /**
     * 保存埋点日志
     *
     * @param request 埋点日志请求
     */
    void saveTrackingLog(SysTrackingLogRequest request);

    /**
     * 根据ID查询埋点日志
     *
     * @param id 日志ID
     * @return 埋点日志VO
     */
    SysTrackingLogVO getTrackingLogById(Long id);

    /**
     * 统计埋点类型数量
     *
     * @param request 查询条件
     * @return 统计结果
     */
    List<EventTypeStatisticsVO> statisticsByEventType(TrackingReportQueryRequest request);

    /**
     * 统计用户活跃量
     *
     * @param request 查询条件
     * @return 统计结果
     */
    List<UserActivityVO> statisticsUserActivity(SysTrackingLogQueryRequest request);
}
