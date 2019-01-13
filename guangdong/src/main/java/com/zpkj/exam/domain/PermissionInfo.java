package com.zpkj.exam.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * PermissionInfo 
 */
@Data
public class PermissionInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id; // 权限表
    private String userId; // 用户id
    private String userType; // 用户类型1admin 2政府管理员 3考生
    private String permission; // 权限
    private java.util.Date createTime; // 创建时间
    private String createUsername; // 创建人
    private java.util.Date updateTime; // 更新时间
    private String updateUsername; // 更新人
    private Integer status; // 状态
}