package com.zpkj.exam.domain.form;

import java.io.Serializable;

import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.UUID;
import java.util.Date;

/**
 * AttachmentInfoForm 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AttachmentInfoForm implements Serializable {
    private static final long serialVersionUID = 1L;
    
	private String id = UUID.randomUUID().toString().replaceAll("-", "");
	private String userId; // 上传人id
	private String attachName; // 文件名
	private String fileType; // 文件类型
	private Integer fileLength; // 文件长度
	private String filePath; // 文件路径
	private String fileUrl; // 文件url
	private String ownerType; // 拥有者类型
	private String ownerId; // 拥有者id
	private Date createTime = new Date(); // 创建时间
	private String createUsername; // 创建人
	private Date updateTime = new Date(); // 更新时间
	private String updateUsername; // 更新人
	private Integer status = 1; // 状态
	private Integer peopleType;
}