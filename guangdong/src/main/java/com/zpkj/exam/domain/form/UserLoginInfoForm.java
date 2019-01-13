package com.zpkj.exam.domain.form;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.UUID;
import java.util.Date;

/**
 * UserLoginInfoForm 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserLoginInfoForm implements Serializable {
    private static final long serialVersionUID = 1L;
    
	private String id = UUID.randomUUID().toString().replaceAll("-", "");
	private String userId; // 用户id
	private String account; // 账号
	private Integer accountType; // 账号类型 1手机号
	private String pwd; // 密码
	private String nickName; // 昵称
	private String userDesc; // 简介、签名等
	private String headImg; // 头像
	private String provinceId; // 省份ID
	private String cityId; // 城市ID
	private String deviceId; // 唯一设备ID
	private Date createTime = new Date(); // 创建时间
	private String createUsername; // 创建人
	private Date updateTime = new Date(); // 更新时间
	private String updateUsername; // 更新人
	private Integer status = 1; // 状态
	private Integer peopleType;//人员类型（1、主要负责人 2、安全生产管理员）
	private String annexUrl;//附件地址
}