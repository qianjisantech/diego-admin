package com.qianjisan.system.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianjisan.system.entity.SysTrackingLog;
import com.qianjisan.system.vo.EventTypeStatisticsVO;
import com.qianjisan.system.vo.UserActivityVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 埋点日志Mapper接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Mapper
public interface SysTrackingLogMapper extends BaseMapper<SysTrackingLog> {

    /**
     * 统计埋点类型数量（按时间维度）
     *
     * @param timeType 时间类型：day, month, year
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计结果
     */
    List<EventTypeStatisticsVO> statisticsByEventType(
            @Param("timeType") String timeType,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime
    );

    /**
     * 统计用户活跃量（按时间维度）
     *
     * @param timeType 时间类型：day, month, year
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计结果
     */
    List<UserActivityVO> statisticsUserActivity(
            @Param("timeType") String timeType,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime
    );
}
