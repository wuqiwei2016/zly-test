package com.zpkj.exam.controller.admin;

import com.zpkj.exam.common.ResultBean;
import com.zpkj.exam.domain.form.ArticleInfoForm;
import com.zpkj.exam.domain.query.*;
import com.zpkj.exam.service.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanshipeng on 2018/6/12.
 */
@RestController
@RequestMapping("/m/count")
@CrossOrigin
public class CountController {

    @Autowired
    ExamPeriodInfoService examPeriodInfoService;
    @Autowired
    ExamInfoService examInfoService;
    @Autowired
    ExamApplyInfoService examApplyInfoService;
    @Autowired
    UserBaseInfoService userBaseInfoService;

    @ApiOperation(value = "获取考试期次", notes = "")
    @RequestMapping(value = "/getExamPeriod", method = RequestMethod.GET)
    public ResultBean getExamPeriod(ExamPeriodInfoQuery query) {
        Map map = new HashMap();
        map.put("total", examPeriodInfoService.find(query).size());
        query.setExamStatus(1);
        map.put("total1", examPeriodInfoService.find(query).size());
        query.setExamStatus(2);
        map.put("total2", examPeriodInfoService.find(query).size());
        query.setExamStatus(3);
        map.put("total3", examPeriodInfoService.find(query).size());
        return new ResultBean(map);
    }

    @ApiOperation(value = "获取考试次数", notes = "")
    @RequestMapping(value = "/getExam", method = RequestMethod.GET)
    public ResultBean getExam(ExamInfoQuery query) {
        Map map = new HashMap();
        query.setResult(6);
        map.put("total", examInfoService.find(query).size());
        query.setResult(4);
        map.put("total1", examInfoService.find(query).size());
        query.setResult(5);
        map.put("total2", examInfoService.find(query).size());
        query.setResult(3);
        map.put("total3", examInfoService.find(query).size());
        return new ResultBean(map);
    }

    @ApiOperation(value = "获取考生总数", notes = "")
    @RequestMapping(value = "/getStudent", method = RequestMethod.GET)
    public ResultBean getStudent(UserBaseInfoQuery query) {
        Map map = new HashMap();
        query.setUserType("3");
        map.put("total", userBaseInfoService.find(query).size());
        ExamApplyInfoQuery query1 = new ExamApplyInfoQuery();
        query1.setCreateTime(new Date());
        query1.setFlag(1);
        map.put("total1", examApplyInfoService.find(query1).size());
        query1.setFlag(2);
        map.put("total2", examApplyInfoService.find(query1).size());
        query1.setFlag(3);
        map.put("total3", examApplyInfoService.find(query1).size());
        return new ResultBean(map);
    }

}
