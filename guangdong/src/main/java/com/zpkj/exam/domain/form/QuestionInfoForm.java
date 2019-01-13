package com.zpkj.exam.domain.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * QuestionInfoForm 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QuestionInfoForm implements Serializable {
    private static final long serialVersionUID = 1L;
    
	private String id = UUID.randomUUID().toString().replaceAll("-", "");
	private String title; // 题干
	private String questionType; // 题库分类
	private Integer type; // 类型1单选 2多选 3判断
	private String answerA; // 答案A
	private String answerB; // 答案B
	private String answerC; // 答案C
	private String answerD; // 答案D
	private String rightAnswer; // 正确答案
	private String answerDescription; // 答案解析
	private Integer difficulty; // 答案解析 1简单 2困难
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createTime = new Date(); // 创建时间
	private String createUsername; // 创建人
	private Date updateTime = new Date(); // 更新时间
	private String updateUsername; // 更新人
	private Integer status = 1; // 状态
}