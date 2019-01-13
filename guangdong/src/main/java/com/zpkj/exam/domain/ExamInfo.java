package com.zpkj.exam.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * ExamInfo
 */
@Data
public class ExamInfo implements Serializable {
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
    private java.util.Date createTime; // 创建时间
    private String createUsername; // 创建人
    private java.util.Date updateTime; // 更新时间
    private String updateUsername; // 更新人
    private Integer status; // 状态

    private String period; // 期次
    private String title; // 考试项目标题
    private java.util.Date examStartTime; // 开始时间
    private java.util.Date examEndTime; // 结束时间
    private String province; // 省
    private String city; // city
    private String area; // 区
    private String address; // 地点
    private Integer examStatus; // 考试状态 1未开始 2已开考 3已结束

    private String realName; // 真实姓名
    private String idCard; // 身份证号
    private String phone; // 电话
    private String workUnit; // 工作单位
    private String peopleType1;
    private String peopleType2;
    private String peopleType3;
    private java.util.Date userCreateTime; // 用户创建时间
    private java.util.Date applyTime; // 申请时间
    private Integer peopleType;//人员类型（1、主要负责人 2、安全生产管理员）
    private String annexUrl;//附件地址

    private List<ExamItemInfo> items;//考试项

    public Integer getExamStatus() {
        if (this.examStartTime != null && this.examEndTime != null) {
            Date now = new Date();
            if (this.examStartTime.getTime() >= now.getTime()) {
                //1未开始
                return 1;
            } else if (this.examStartTime.getTime() < now.getTime() && this.examEndTime.getTime() > now.getTime()) {
                //2已开考
                return 2;
            } else {
                //3已结束
                return 3;
            }
        } else {
            return 0;
        }
    }
}