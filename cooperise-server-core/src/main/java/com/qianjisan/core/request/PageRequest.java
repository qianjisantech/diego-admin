package com.qianjisan.core.request;



import com.qianjisan.core.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 分页请求基类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageRequest extends PageQuery {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 当前页码（别名，�?current 相同�?
     */
    public Integer getCurrent() {
        return super.getCurrent();
    }

    public void setCurrent(Integer current) {
        super.setCurrent(current);
    }

    /**
     * 每页大小（别名，�?size 相同�?
     */
    public Integer getPageSize() {
        return super.getSize();
    }

    public void setPageSize(Integer pageSize) {
        super.setSize(pageSize);
    }
}
