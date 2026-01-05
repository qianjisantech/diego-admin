package com.qianjisan.console.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 视图文件夹创建/更新请求
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class ViewFolderRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件夹名称
     */
    @NotBlank(message = "文件夹名称不能为空")
    @Size(max = 200, message = "文件夹名称长度不能超过200个字符")
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

