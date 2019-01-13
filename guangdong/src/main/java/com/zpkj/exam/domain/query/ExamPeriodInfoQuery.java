package com.zpkj.exam.domain.query;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * ExamPeriodInfoQuery
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ExamPeriodInfoQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id; // 考试期次表
    private String period; // 期次
    private String title; // 考试项目标题
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date startTime; // 开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date endTime; // 结束时间
    private String province; // 省
    private String city; // city
    private String area; // 区
    private String address; // 地点
    private java.util.Date createTime = new Date(); // 创建时间
    private String createUsername; // 创建人
    private java.util.Date updateTime; // 更新时间
    private String updateUsername; // 更新人
    private Integer status; // 状态

    private Integer page = 1;
    private Integer limit = 20;
    private String search;

    private String timeQuery; // 获取开始时间位于当前日期后的考期
    private Integer examStatus; // 考试状态 1未开始 2已开考 3已结束
}