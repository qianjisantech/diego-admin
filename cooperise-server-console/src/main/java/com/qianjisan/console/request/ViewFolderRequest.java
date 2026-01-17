package com.qianjisan.console.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 视图文件夹创�?更新请求
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class ViewFolderRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件夹名�?
     */
    @NotBlank(message = "文件夹名称不能为�?)
    @Size(max = 200, message = "文件夹名称长度不能超�?00个字�?)
    private String name;

    /**
     * 父文件夹ID
     */
    private Long parentId;

    /**
     * 排序顺序
     */
    private Integer sortOrder;
}

