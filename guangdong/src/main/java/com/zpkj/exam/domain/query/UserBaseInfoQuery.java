package com.zpkj.exam.domain.query;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * UserBaseInfoQuery
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserBaseInfoQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id; // 用户基本信息表
    private String realName; // 真实姓名
    private String idCard; // 身份证号
    private Integer sex; // 性别 1男 2女
    private java.util.Date birthday; // 生日
    private String phone; // 电话
    private String email; // 邮箱
    private String province; // 省
    private String city; // 市
    private String area; // 区
    private String address; // 地址
    private String education; // 学历
    private String workUnit; // 工作单位
    private String postCode; // 邮政编码
    private String fixedTelephone; // 固定电话
    private String position; // 职位
    private String workNumber; // 工号
    private java.util.Date createTime; // 创建时间
    private String createUsername; // 创建人
    private java.util.Date updateTime; // 更新时间
    private String updateUsername; // 更新人
    private Integer status; // 状态
    private String peopleType1; // 人员类型1
    private String peopleType2; // 人员类型2
    private String peopleType3; // 人员类型3

    private Integer page = 1;
    private Integer limit = 20;
    private String search;

    private Integer isApply; // 是否已申请考试 1申请 2未申请
    private String userType; // 用户类型1admin 2政府管理员 3考生
}