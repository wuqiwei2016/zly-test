package com.zpkj.exam.domain.form;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.UUID;
import java.util.Date;

/**
 * ExamApplyInfoForm 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ExamApplyInfoForm implements Serializable {
    private static final long serialVersionUID = 1L;
    
	private String id = UUID.randomUUID().toString().replaceAll("-", "");
	private String userId; // 用户id
	private Date applyTime; // 申请时间
	private String examId; // 考试id
	private Date createTime = new Date(); // 创建时间
	private String createUsername; // 创建人
	private Date updateTime = new Date(); // 更新时间
	private String updateUsername; // 更新人
	private Integer status = 1; // 状态
}