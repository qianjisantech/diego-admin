package com.dcp.common.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FeedbackPageVo<T> extends Page<T>{

    private  long all;

    private long closed;

    private  long opened;

    public FeedbackPageVo(Integer current, Integer size) {
        super(current, size);
    }

    public FeedbackPageVo(long current, long size, long total) {
        super(current, size, total);
    }
}
