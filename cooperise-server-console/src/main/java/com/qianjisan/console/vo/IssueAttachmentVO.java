package com.qianjisan.console.vo;

import com.qianjisan.core.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 事项附件VO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IssueAttachmentVO extends BaseVO {

    /**
     * 事项ID
     */
    private Long issueId;

    /**
     * 文件名
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

    /**
     * 上传者用户名
     */
    private String uploaderUsername;

    /**
     * 上传者昵称
     */
    private String uploaderNickname;
}
