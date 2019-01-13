package com.zpkj.exam.domain.form;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.UUID;
import java.util.Date;

/**
 * PermissionInfoForm 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PermissionInfoForm implements Serializable {
    private static final long serialVersionUID = 1L;
    
	private String id = UUID.randomUUID().toString().replaceAll("-", "");
	private String userId; // 用户id
	private String userType; // 用户类型1admin 2政府管理员 3考生
	private String permission; // 权限
	private Date createTime = new Date(); // 创建时间
	private String createUsername; // 创建人
	private Date updateTime = new Date(); // 更新时间
	private String updateUsername; // 更新人
	private Integer status = 1; // 状态
}