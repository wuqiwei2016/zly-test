package com.zpkj.exam.controller.admin;

import com.github.pagehelper.Page;
import com.zpkj.exam.common.ResultBean;
import com.zpkj.exam.domain.ArticleInfo;
import com.zpkj.exam.domain.ExamInfo;
import com.zpkj.exam.domain.ExamPeriodInfo;
import com.zpkj.exam.domain.form.ArticleInfoForm;
import com.zpkj.exam.domain.form.ExamPeriodInfoForm;
import com.zpkj.exam.domain.query.ExamInfoQuery;
import com.zpkj.exam.domain.query.ExamPeriodInfoQuery;
import com.zpkj.exam.filter.XssFilter;
import com.zpkj.exam.service.ArticleInfoService;
import com.zpkj.exam.service.ExamInfoService;
import com.zpkj.exam.service.ExamPeriodInfoService;
import com.zpkj.exam.util.ExcelUtils;
import com.zpkj.exam.util.MessageUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by wanshipeng on 2018/6/12.
 */
@RestController
@RequestMapping("/m/examPeriod")
@CrossOrigin
public class ExamPeriodController {
    @Autowired
    ExamPeriodInfoService examPeriodService;
    @Autowired
    ArticleInfoService articleInfoService;
    @Autowired
    ExamInfoService examInfoService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ExamPeriodController.class);
    @ApiOperation(value = "获取考试期次列表", notes = "")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResultBean getExamPeriodInfoList(ExamPeriodInfoQuery query) {
        Page<ExamPeriodInfo> byPage = examPeriodService.findByPage(query);
        for (ExamPeriodInfo examPeriodInfo: byPage) {
            String id = examPeriodInfo.getId();
            LOGGER.info("当前考试其次的Id为："+id);
            //查询当前考试期次对应的条数是否大于0 if大于0 那就修改状态
            int count = examInfoService.findExamListByexamPeriodId(id);
            if (count>0){
                //修改状态为1;
                examPeriodInfo.setStatusExamInform(1);
            }
        }
        return new ResultBean(byPage);
    }

    @ApiOperation(value = "获取考试期次列表", notes = "")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResultBean getAll(ExamPeriodInfoQuery query) {
        if (query.getTimeQuery() != null && query.getTimeQuery().equals("1")) {
            query.setStartTime(new Date());
        }
        return new ResultBean(examPeriodService.find(query));
    }

    @ApiOperation(value = "创建考试期次", notes = "根据ExamPeriodInfo对象创建考试期次")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "考试期次ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "period", value = "期次", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "title", value = "考试项目标题", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "province", value = "省", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "city", value = "市", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "area", value = "区", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "address", value = "地点", required = true, dataType = "String", paramType = "query")
    })
    public ResultBean postExamPeriodInfo(@ApiIgnore ExamPeriodInfoForm examPeriodForm) {
        examPeriodService.add(examPeriodForm);
        return new ResultBean();
    }

    @ApiOperation(value = "获取考试期次详细信息", notes = "根据url的id来获取考试期次详细信息")
    @ApiImplicitParam(name = "id", value = "考试期次ID", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResultBean getExamPeriodInfoById(@PathVariable("id") String id) {
        return new ResultBean(examPeriodService.get(id));
    }

    @ApiOperation(value = "更新考试期次详细信息", notes = "根据url的id来指定更新对象，并根据传过来的examPeriod信息来更新考试期次详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "考试期次ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "examPeriodForm", value = "考试期次详细实体ExamPeriodInfoform", required = true, dataType = "ExamPeriodInfoform")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResultBean updateExamPeriodInfo(@PathVariable("id") String id, ExamPeriodInfoForm examPeriodform) {
        examPeriodService.update(examPeriodform);
        return new ResultBean();
    }

    @ApiOperation(value = "删除考试期次", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "考试期次ID", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResultBean deleteExamPeriodInfo(@PathVariable("id") String id) {
        examPeriodService.delete(id);
        return new ResultBean();
    }

    @ApiOperation(value = "创建通知", notes = "根据考试期次id创建通知")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "考试期次ID", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/notice/{id}", method = RequestMethod.POST)
    public ResultBean notice(@PathVariable("id") String id) {
        //查询是否已创建过通知
        ArticleInfo byOwnerId = articleInfoService.getByOwnerId(id);
        if (articleInfoService.getByOwnerId(id) == null) {
            ExamPeriodInfo info = examPeriodService.get(id);
            //创建通知
            ArticleInfoForm form = new ArticleInfoForm();
            form.setArticleType("1");
            form.setTitle("关于【" + info.getPeriod() + "】的考试通知");
            form.setOwnerId(id);
            articleInfoService.add(form);
        }
        //查询未通知的考生
        ExamInfoQuery query = new ExamInfoQuery();
        query.setExamPeriodId(id);
        LOGGER.info("发送通知的条件为："+query.getExamPeriodId());
        query.setIsInform(2);
        List<ExamInfo> list = examInfoService.find(query);
        for (ExamInfo info : list) {
            //循环发短信
            MessageUtil.sendMessage("活动验证", "SMS_139976476", "{\"name\":\"" + info.getRealName() + "\",\"address\":\"" + info.getAddress() + "\",\"exam\":\"" + info.getPeriod() + "\",\"system\":\"【吉林省安全人员考试平台】\"}", info.getPhone());
            //改为已通知
            examInfoService.changeIsInform(info.getId(), 1);
            LOGGER.info("短信发送成功");
        }
        return new ResultBean();
    }

    @GetMapping("/download")
    public void download(ExamInfoQuery query, HttpServletResponse response) {
        List<ExamInfo> list = examInfoService.find(query);
        List<Map> data = new ArrayList<>();
        list.forEach(row -> {
            Map<Integer, String> map = new HashMap<>();
            map.put(0, row.getRealName());
            map.put(1, row.getIdCard());
            map.put(2, row.getPhone());
            map.put(3, row.getWorkUnit());
            if (row.getResult() != null) {
                if (row.getResult() == 1) {
                    map.put(4, "合格");
                } else {
                    map.put(4, "未合格");
                }
            } else {
                map.put(4, "弃考");
            }
            data.add(map);
        });
        try {
            String sheetName = "考试数据";
            String fileName = "考试数据";
            int columnNumber = 5;
            int[] columnWidth = {20, 20, 20, 20, 20};
            String[] columnName = {"考生姓名", "身份证号", "手机号",
                    "所在企业", "考试状态"};
            ExcelUtils.ExportWithResponse(sheetName, "", fileName,
                    columnNumber, columnWidth, columnName, data, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
