package com.zpkj.exam.domain.form;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.UUID;
import java.util.Date;

/**
 * CarouselInfoForm 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CarouselInfoForm implements Serializable {
    private static final long serialVersionUID = 1L;
    
	private String id = UUID.randomUUID().toString().replaceAll("-", "");
	private String imgPath; // 图片
	private String title; // 标题
	private String link; // 链接
	private Integer sortOrder; // 排序
	private Date createTime = new Date(); // 创建时间
	private String createUsername; // 创建人
	private Date updateTime = new Date(); // 更新时间
	private String updateUsername; // 更新人
	private Integer status = 1; // 状态
}