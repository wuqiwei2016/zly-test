package com.zpkj.exam.domain.form;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.UUID;
import java.util.Date;

/**
 * ExamPeriodInfoForm
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ExamPeriodInfoForm implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id = UUID.randomUUID().toString().replaceAll("-", "");
    private String period; // 期次
    private String title; // 考试项目标题
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime; // 开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime; // 结束时间
    private String province; // 省
    private String city; // city
    private String area; // 区
    private String address; // 地点
    private Date createTime = new Date(); // 创建时间
    private String createUsername; // 创建人
    private Date updateTime = new Date(); // 更新时间
    private String updateUsername; // 更新人
    private Integer status = 1; // 状态
}