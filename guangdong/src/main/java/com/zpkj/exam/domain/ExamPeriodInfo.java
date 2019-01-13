package com.zpkj.exam.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * ExamPeriodInfo
 */
@Data
public class ExamPeriodInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id; // 考试期次表
    private String period; // 期次
    private String title; // 考试项目标题
    private java.util.Date startTime; // 开始时间
    private java.util.Date endTime; // 结束时间
    private String province; // 省
    private String city; // city
    private String area; // 区
    private String address; // 地点
    private java.util.Date createTime; // 创建时间
    private String createUsername; // 创建人
    private java.util.Date updateTime; // 更新时间
    private String updateUsername; // 更新人
    private Integer status; // 状态

    private Integer totalNum; // 已分配考生
    private Integer passNum; // 考试合格人数
    private Integer nullNum; // 弃考人数
    private Integer examStatus; // 考试状态 1未开始 2已开考 3已结束

    private String provinceName; // 省
    private String cityName; // city
    private String areaName; // 区

    private Integer statusExamInform=0;
    public Integer getExamStatus() {
        if (this.startTime != null && this.endTime != null) {
            Date now = new Date();
            if (this.startTime.getTime() >= now.getTime()) {
                //1未开始
                return 1;
            } else if (this.startTime.getTime() < now.getTime() && this.endTime.getTime() > now.getTime()) {
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