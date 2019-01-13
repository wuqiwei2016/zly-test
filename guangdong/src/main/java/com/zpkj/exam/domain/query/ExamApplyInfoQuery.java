package com.zpkj.exam.domain.query;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ExamApplyInfoQuery
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ExamApplyInfoQuery implements Serializable {
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

    private Integer page = 1;
    private Integer limit = 20;
    private String search;

    private Integer flag; //考生管理页面查询标记 1 是考生管理页面
    private String realName;
    private String idCard;
    private Integer peopleType;//人员类型（1、主要负责人 2、安全生产管理员）
    private String annexUrl;//附件地址
}