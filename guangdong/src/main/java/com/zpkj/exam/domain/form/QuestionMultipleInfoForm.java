package com.zpkj.exam.domain.form;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.UUID;
import java.util.Date;

/**
 * QuestionMultipleInfoForm 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QuestionMultipleInfoForm implements Serializable {
    private static final long serialVersionUID = 1L;
    
	private String id = UUID.randomUUID().toString().replaceAll("-", "");
	@NotBlank(message = "题干不能为空")
	@Length(max = 255, message = "题干最大长度在255字以内")
	private String title; // 题干
	private String questionType; // 题库分类
	@NotBlank(message = "选项A不能为空")
	@Length(max = 255, message = "选项A最大长度在255字以内")
	private String answerA; // 答案A
	@NotBlank(message = "选项B不能为空")
	@Length(max = 255, message = "选项B最大长度在255字以内")
	private String answerB; // 答案B
	@NotBlank(message = "选项C不能为空")
	@Length(max = 255, message = "选项C最大长度在255字以内")
	private String answerC; // 答案C
	@NotBlank(message = "选项D不能为空")
	@Length(max = 255, message = "选项D最大长度在255字以内")
	private String answerD; // 选项D
	@NotBlank(message = "正确答案不能为空")
	@Length(max = 255, message = "正确答案最大长度在255字以内")
	private String rightAnswer; // 正确答案
	private String answerDescription; // 答案解析
	private Integer difficulty; // 答案解析 1简单 2困难
	private Date createTime = new Date(); // 创建时间
	private String createUsername; // 创建人
	private Date updateTime = new Date(); // 更新时间
	private String updateUsername; // 更新人
	private Integer status = 1; // 状态
	private String peopleType1; // 人员类型1
	private String peopleType2; // 人员类型2
	private String peopleType3; // 人员类型3
}