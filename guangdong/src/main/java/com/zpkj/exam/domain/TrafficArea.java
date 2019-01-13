package com.zpkj.exam.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * TrafficArea 
 */
@Data
public class TrafficArea implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer areaId; // 主键（无意义）
    private String areaNo; // 区域编号（国家行政编码）
    private String areaParentno; // 父区域编号
    private String areaName; // 区域名称
    private String areaShortname; // 区域简称（针对省份）
    private String areaFullspell; // 区域全拼（针对市）
    private String areaShortspell; // 区域简拼（针对市）
    private String areaCode; // 区域区号（针对市）
    private Integer areaRank; // 区域等级
    private String areaZipcode; // 区域邮政编码
}