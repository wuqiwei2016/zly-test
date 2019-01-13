package com.zpkj.exam.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * UserBaseInfo
 */
@Data
public class UserBaseInfo implements Serializable {
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
    //抓拍图片
    private String account; // 账号
    private String pwd; // 密码
    private String permission; // 权限
    private String headImg; // 头像
    private String userType; // 用户类型
    private Integer editStatus; // 1 可编辑 2 不可编辑

    private String applyTime; //申请时间

    private Date startTime; // 开始时间
    private Date endTime; // 结束时间
    private Integer examStatus; // 考试状态 1待分配 2待考试 3已结束

    private String provinceName; // 省
    private String cityName; // 市
    private String areaName; // 区

    private Integer peopleType;//人员类型（1、主要负责人 2、安全生产管理员）
    private String annexUrl;//附件地址
    private String annexName;//附件名称
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