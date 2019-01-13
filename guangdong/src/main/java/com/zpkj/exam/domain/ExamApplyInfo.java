package com.zpkj.exam.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * ExamApplyInfo
 */
@Data
public class ExamApplyInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id; // 考试申请表
    private String userId; // 用户id
    private java.util.Date applyTime; // 申请时间
    private String examId; // 考试id
    private java.util.Date createTime; // 创建时间
    private String createUsername; // 创建人
    private java.util.Date updateTime; // 更新时间
    private String updateUsername; // 更新人
    private Integer status; // 状态

    private String realName; // 真实姓名
    private String idCard; // 身份证号
    private String phone; // 电话
    private String workUnit; // 工作单位
    private java.util.Date userCreateTime; // 用户创建时间

    private Date startTime; // 开始时间
    private Date endTime; // 结束时间
    private Integer examStatus; // 考试状态 1待分配 2待考试 3已结束
    private Integer peopleType;//人员类型（1、主要负责人 2、安全生产管理员）
    private String annexUrl;//附件地址
    public Integer getExamStatus() {
        if (this.startTime != null && this.endTime != null) {
            Date now = new Date();
            if (this.startTime.getTime() >= now.getTime()) {
                //1待考试
                return 2;
            } else if (this.startTime.getTime() < now.getTime() && this.endTime.getTime() > now.getTime()) {
                //2考试中
                return 3;
            } else {
                //3已结束
                return 4;
            }
        } else {
            //1待分配
            return 1;
        }
    }
}