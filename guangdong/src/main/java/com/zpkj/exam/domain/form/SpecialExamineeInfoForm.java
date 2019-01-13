package com.zpkj.exam.domain.form;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.UUID;
import java.util.Date;

/**
 * SpecialExamineeInfoForm 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SpecialExamineeInfoForm implements Serializable {
    private static final long serialVersionUID = 1L;
    
	private String id = UUID.randomUUID().toString().replaceAll("-", "");
	@NotBlank(message = "姓名不能为空")
	@Length(max = 45, message = "姓名最大长度在45字以内")
	private String realName; // 真实姓名
	@NotBlank(message = "身份证号不能为空")
	@Length(max = 45, message = "身份证号最大长度在45字以内")
	private String idCard; // 身份证号
	@NotBlank(message = "电话不能为空")
	@Length(max = 45, message = "电话最大长度在45字以内")
	private String phone; // 电话
	private Date createTime = new Date(); // 创建时间
	private String createUsername; // 创建人
	private Date updateTime = new Date(); // 更新时间
	private String updateUsername; // 更新人
	private Integer status = 1; // 状态
}