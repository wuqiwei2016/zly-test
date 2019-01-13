package com.zpkj.exam.domain.query;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ExamInfoQuery 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ExamInfoQuery implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id; // 考试表
    private String userId; // userId
    private String examPeriodId; // 考试期次id
    private java.util.Date startTime; // 开始时间
    private java.util.Date endTime; // 结束时间
    private String totalScore; // 总分
    private Integer result; // 结果 1 通过 2 未通过
    private String picture1; // 抓拍图片1
    private String picture2; // 抓拍图片2
    private String picture3; // 抓拍图片3
    private Integer isInform; // 是否通知 1 通知 2 未通知
    private java.util.Date createTime = new Date(); // 创建时间
    private String createUsername; // 创建人
    private java.util.Date updateTime; // 更新时间
    private String updateUsername; // 更新人
    private Integer status; // 状态
	
    private Integer page = 1;
    private Integer limit = 20;
    private String search;
    private Integer peopleType;//人员类型（1、主要负责人 2、安全生产管理员）
    private String annexUrl;//附件地址
}