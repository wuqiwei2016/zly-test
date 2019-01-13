package com.zpkj.exam.controller.common;

import com.github.pagehelper.Page;
import com.zpkj.exam.common.ResultBean;
import com.zpkj.exam.domain.ArticleInfo;
import com.zpkj.exam.domain.ExamInfo;
import com.zpkj.exam.domain.query.ArticleInfoQuery;
import com.zpkj.exam.domain.query.ExamInfoQuery;
import com.zpkj.exam.filter.XssFilter;
import com.zpkj.exam.service.ArticleInfoService;
import com.zpkj.exam.service.ExamInfoService;
import com.zpkj.exam.service.ExamPeriodInfoService;
import com.zpkj.exam.util.NumberUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * Created by wanshipeng on 2018/6/12.
 */
@RestController
@RequestMapping("/article")
@CrossOrigin
public class ForeArticleController {

    @Autowired
    private ArticleInfoService articleInfoService;
    @Autowired
    private ExamPeriodInfoService examPeriodInfoService;
    @Autowired
    private ExamInfoService examInfoService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ForeArticleController.class);
    @ApiOperation(value = "获取文章列表", notes = "")
    @ApiImplicitParam(name = "articleType", value = "类型", paramType = "query", required = true, dataType = "String")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResultBean getArticleInfoList(@ApiIgnore ArticleInfoQuery query) {
        LOGGER.info("开始获取文章列表");
        Page<ArticleInfo> byPage = articleInfoService.findByPage(query);
        return new ResultBean(articleInfoService.findByPage(query));
    }

    @ApiOperation(value = "获取文章详细信息", notes = "根据url的id来获取文章详细信息")
    @ApiImplicitParam(name = "id", value = "文章ID", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResultBean getArticleInfoById(@PathVariable("id") String id) {
        LOGGER.info("此处正在查询考试通知参数为："+id);
        ArticleInfo articleInfo = articleInfoService.get(id);
        if (articleInfo.getArticleType().equals("1")) {
            //通知类型
            //获取考期信息
            articleInfo.setExamPeriodInfo(examPeriodInfoService.get(articleInfo.getOwnerId()));
            //获取考试信息
            ExamInfoQuery query = new ExamInfoQuery();
            query.setExamPeriodId(articleInfo.getOwnerId());
            List<ExamInfo> examInfos = examInfoService.find(query);
            for (ExamInfo exam : examInfos){
                exam.setPhone(NumberUtil.hideAllPhoneNum(exam.getPhone()));
                exam.setIdCard(NumberUtil.hideAllIdCardNum(exam.getIdCard()));
            }
            articleInfo.setExamInfoList(examInfos);
        } else if (articleInfo.getArticleType().equals("4")) {
            //考试通过公告
            //获取考试信息
            ExamInfo examInfo = examInfoService.get(articleInfo.getOwnerId());
            examInfo.setPhone(NumberUtil.hideAllPhoneNum(examInfo.getPhone()));
            examInfo.setIdCard(NumberUtil.hideAllIdCardNum(examInfo.getIdCard()));
            articleInfo.setExamInfo(examInfo);
        }

//        articleInf(mallptorder.getUserId().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
        return new ResultBean(articleInfo);
    }
}
