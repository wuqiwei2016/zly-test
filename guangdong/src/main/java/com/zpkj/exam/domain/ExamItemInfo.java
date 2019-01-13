package com.zpkj.exam.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * ExamItemInfo 
 */
@Data
public class ExamItemInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id; // 考试题目表
    private String examId; // 考试id
    private String questionId; // 考题id
    private Integer orderNum; // 题目序号
    private String userAnswer; // 考生答案
    private Integer result; // 结果 1正确 2错误
    private String title; // 题干
    private String questionType; // 题库分类
    private Integer type; // 类型1单选 2多选 3判断
    private String answerA; // 答案A
    private String answerB; // 答案B
    private String answerC; // 答案C
    private String answerD; // 答案D
    private String rightAnswer; // 正确答案
    private String answerDescription; // 答案解析
    private Integer difficulty; // 答案解析 1简单 2困难
    private java.util.Date createTime; // 创建时间
    private String createUsername; // 创建人
    private java.util.Date updateTime; // 更新时间
    private String updateUsername; // 更新人
    private Integer status; // 状态
}