package com.zpkj.exam.domain.query;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * QuestionJudgeInfoQuery 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QuestionJudgeInfoQuery implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id; // 判断题表
    private String title; // 题干
    private String questionType; // 题库分类
    private String rightAnswer; // 正确答案
    private String answerDescription; // 答案解析
    private Integer difficulty; // 答案解析 1简单 2困难
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
}