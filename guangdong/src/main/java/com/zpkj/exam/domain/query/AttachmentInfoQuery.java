package com.zpkj.exam.domain.query;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * AttachmentInfoQuery 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AttachmentInfoQuery implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id; // 附件表
    private String userId; // 上传人id
    private String attachName; // 文件名
    private String fileType; // 文件类型
    private Integer fileLength; // 文件长度
    private String filePath; // 文件路径
    private String fileUrl; // 文件url
    private String ownerType; // 拥有者类型
    private String ownerId; // 拥有者id
    private java.util.Date createTime; // 创建时间
    private String createUsername; // 创建人
    private java.util.Date updateTime; // 更新时间
    private String updateUsername; // 更新人
    private Integer status; // 状态
	
    private Integer page = 1;
    private Integer limit = 20;
    private String search;
}