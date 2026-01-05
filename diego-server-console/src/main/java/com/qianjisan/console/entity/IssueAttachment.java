package com.qianjisan.console.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qianjisan.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 事项附件表
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("issue_attachment")
public class IssueAttachment extends BaseEntity {

    /**
     * 附件ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 事项ID
     */
    @TableField("issue_id")
    private Long issueId;

    /**
     * 文件名
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 文件路径
     */
    @TableField("file_path")
    private String filePath;

    /**
     * 文件大小（字节）
     */
    @TableField("file_size")
    private Long fileSize;

    /**
     * 文件类型
     */
    @TableField("file_type")
    private String fileType;

    /**
     * 上传者ID
     */
    @TableField("uploader_id")
    private Long uploaderId;
}
