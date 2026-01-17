package com.qianjisan.console.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 事项附件请求�?
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class IssueAttachmentRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 附件ID（更新时使用�?
     */
    private Long id;

    /**
     * 事项ID
     */
    private Long issueId;

    /**
     * 文件�?
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件大小（字节）
     */
    private Long fileSize;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 上传者ID
     */
    private Long uploaderId;
}

