package com.zpkj.exam.controller.admin;

import com.github.pagehelper.Page;
import com.zpkj.exam.common.ResultBean;
import com.zpkj.exam.domain.ExamApplyInfo;
import com.zpkj.exam.domain.ExamInfo;
import com.zpkj.exam.domain.ExamPeriodInfo;
import com.zpkj.exam.domain.UserBaseInfo;
import com.zpkj.exam.domain.form.ArticleInfoForm;
import com.zpkj.exam.domain.form.ExamInfoForm;
import com.zpkj.exam.domain.query.ExamApplyInfoQuery;
import com.zpkj.exam.domain.query.ExamInfoQuery;
import com.zpkj.exam.domain.query.UserBaseInfoQuery;
import com.zpkj.exam.filter.XssFilter;
import com.zpkj.exam.service.ExamApplyInfoService;
import com.zpkj.exam.service.ExamInfoService;
import com.zpkj.exam.service.UserBaseInfoService;
import com.zpkj.exam.util.MessageUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Created by wanshipeng on 2018/6/12.
 */
@RestController
@RequestMapping("/m/exam")
@CrossOrigin
public class ExamController {

    @Autowired
    ExamInfoService examService;
    @Autowired
    ExamApplyInfoService examApplyInfoService;
    @Autowired
    UserBaseInfoService userBaseInfoService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExamController.class);

    @ApiOperation(value = "获取考试列表", notes = "")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResultBean getExamInfoList(@ModelAttribute ExamInfoQuery query) {
        return new ResultBean(examService.findByPage(query));
    }

    @ApiOperation(value = "获取考生去重后的考试列表", notes = "")
    @RequestMapping(value = "/distinct", method = RequestMethod.GET)
    public ResultBean findDistinctByPage(@ModelAttribute ExamInfoQuery query) {
        return new ResultBean(examService.findDistinctByPage(query));
    }

    @ApiOperation(value = "获取已经考试人员列表", notes = "")
    @RequestMapping(value = "endExamPeople", method = RequestMethod.GET)
    public ResultBean getendExamPeopleInfoList(@ModelAttribute ExamInfoQuery query) {
        Page<ExamInfo> byPage = examService.findByPage(query);
        LOGGER.info("获取已经考试人员列表数据为：" + byPage.toString());
        return new ResultBean(examService.findByPageTwo(query));
    }
    /*@ApiOperation(value = "创建考试", notes = "根据ExamInfo对象创建考试")
    @ApiImplicitParam(name = "examForm", value = "考试详细实体ExamInfoForm", required = true, dataType = "ExamInfoForm")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResultBean postExamInfo(ExamInfoForm examForm) {
        LOGGER.info("Start 创建考试请求参数为： "+examForm.toString());
        if (examForm.getUserId() != null && !examForm.getUserId().equals("")) {
            //通过考生添加
            String userIds = examForm.getUserId();
            LOGGER.info("Start 考生UserID为： "+userIds);
            String[] userIdss = userIds.split(",");
            for (String userId : userIdss) {
                if (!userId.equals("")) {
                    ExamInfoQuery query = new ExamInfoQuery();
                    query.setUserId(userId);
                    query.setStatus(1);
                    List<ExamInfo> list = examService.find(query);
                    boolean flag = true;
                    if (list.size() > 0) {
                        ExamInfo info = list.get(0);
                        if (info.getExamStatus() != 2 && !examForm.getExamPeriodId().equals(info.getExamPeriodId())) {
                            try {
                                int i = examService.delete(info.getId());
                                LOGGER.info("Start delect的参数为："+i);
                            }catch (Exception e){
                                //没有已申请考试的考生信息
                                LOGGER.info("Start 申请考试异常：");
                                return new ResultBean(1, "申请考试异常");
                            }

                        } else {
                            flag = false;
                        }
                    }
                    if (flag) {
                        examForm.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                        examForm.setUserId(userId);
                        examService.add(examForm);
                    }
                }
            }
            return new ResultBean(0);
        } else if (examForm.getRealName() != null && !examForm.getRealName().equals("") && examForm.getIdCard() != null && !examForm.getIdCard().equals("")) {
            //通过姓名身份证号添加
            //通过姓名、身份证号查询已申请考试的考生信息
            UserBaseInfoQuery query = new UserBaseInfoQuery();
            query.setRealName(examForm.getRealName());
            query.setIdCard(examForm.getIdCard());
            query.setIsApply(1);
            List<UserBaseInfo> list = userBaseInfoService.find(query);
            LOGGER.info("Start 查询UserBaseInfo考生信息为： "+list.toString());
            if (list.size() > 0) {
                //查询考生是否有分配的考试记录
                ExamInfoQuery query2 = new ExamInfoQuery();
                query2.setUserId(list.get(0).getId());
                LOGGER.info("Start 查询考生是否有查询考生记录为： "+query2.toString());
                List<ExamInfo> list2 = examService.find(query2);
                if (list2.size() > 0) {
                    ExamInfo examInfo = list2.get(0);
                    if (examInfo.getExamStatus() == 1) {
                        if (!examInfo.getExamPeriodId().equals(examForm.getExamPeriodId())) {
                            //删除、重新分配
                            examService.delete(examInfo.getId());
                            examForm.setUserId(list.get(0).getId());
                            examService.add(examForm);
                            return new ResultBean(0, examForm.getId(), list.get(0));
                        } else {
                            return new ResultBean(1, "本次考期已添加过此考生");
                        }

                    } else if (examInfo.getExamStatus() == 2) {
                        return new ResultBean(1, "此考生已开考");
                    } else if (examInfo.getExamStatus() == 3) {
                        examForm.setUserId(list.get(0).getId());
                        examService.add(examForm);
                        return new ResultBean(0, examForm.getId(), list.get(0));
                    } else {
                        return new ResultBean();
                    }
                } else {
                    examForm.setUserId(list.get(0).getId());
                    examService.add(examForm);
                    return new ResultBean(0, examForm.getId(), list.get(0));
                }
            } else {
                //没有已申请考试的考生信息
                return new ResultBean(1, "没有已申请考试的考生信息");
            }
        } else {
            return new ResultBean(1, "信息不全");
        }
    }*/

    @ApiOperation(value = "创建考试", notes = "根据ExamInfo对象创建考试")
    @ApiImplicitParam(name = "examForm", value = "考试详细实体ExamInfoForm", required = true, dataType = "ExamInfoForm")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResultBean postExamInfo(ExamInfoForm examForm) {
        if (examForm.getUserId() != null && !examForm.getUserId().equals("")) {
            //通过考生添加
            String userIds = examForm.getUserId();
            String[] userIdss = userIds.split(",");
            for (String userId : userIdss) {
                if (!userId.equals("")) {
                    //查询此考生是否已经分配过此考期
                    ExamInfoQuery query = new ExamInfoQuery();
                    query.setUserId(userId);
                    query.setExamPeriodId(examForm.getExamPeriodId());
                    List<ExamInfo> list = examService.find(query);
                    if (list.size() == 0) {
                        //分配
                        examForm.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                        examForm.setUserId(userId);
                        examService.add(examForm);
                        examForm.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                        examService.add(examForm);
                        examForm.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                        examService.add(examForm);
                    }
                }
            }
            return new ResultBean();
        } else if (examForm.getRealName() != null && !examForm.getRealName().equals("") && examForm.getIdCard() != null && !examForm.getIdCard().equals("")) {
            //通过姓名身份证号添加
            //通过姓名、身份证号查询已申请考试的考生信息
            UserBaseInfoQuery query = new UserBaseInfoQuery();
            query.setRealName(examForm.getRealName());
            query.setIdCard(examForm.getIdCard());
            List<UserBaseInfo> list = userBaseInfoService.find(query);
            if (list.size() > 0) {
                UserBaseInfo userInfo = list.get(0);
                //查询此考生是否已经分配过此考期
                ExamInfoQuery query2 = new ExamInfoQuery();
                query2.setUserId(userInfo.getId());
                query2.setExamPeriodId(examForm.getExamPeriodId());
                List<ExamInfo> list2 = examService.find(query2);
                if (list2.size() == 0) {
                    //分配
                    examForm.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                    examForm.setUserId(userInfo.getId());
                    examService.add(examForm);
                    examForm.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                    examService.add(examForm);
                    examForm.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                    examService.add(examForm);
                    return new ResultBean();
                } else {
                    return new ResultBean(1, "本次考期已添加过此考生");
                }
            } else {
                //没有已申请考试的考生信息
                return new ResultBean(1, "没有此考生信息");
            }
        } else {
            return new ResultBean(1, "信息不全");
        }
    }

    @ApiOperation(value = "获取考试详细信息", notes = "根据url的id来获取考试详细信息")
    @ApiImplicitParam(name = "id", value = "考试ID", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResultBean getExamInfoById(@PathVariable("id") String id) {
        return new ResultBean(examService.get(id));
    }

    @ApiOperation(value = "更新考试详细信息", notes = "根据url的id来指定更新对象，并根据传过来的exam信息来更新考试详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "考试ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "examForm", value = "考试详细实体ExamInfoform", required = true, dataType = "ExamInfoform")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResultBean updateExamInfo(@PathVariable("id") String id, ExamInfoForm examform) {
        examService.update(examform);
        return new ResultBean();
    }

    @ApiOperation(value = "删除考试", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "考试ID", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResultBean deleteExamInfo(@PathVariable("id") String id) {
        examService.delete(id);
        return new ResultBean();
    }

    @ApiOperation(value = "删除考试", notes = "根据url的id来指定删除对象")
    @RequestMapping(value = "/delete/{examPeriodId}_{userId}", method = RequestMethod.DELETE)
    public ResultBean deleteExamInfoByUserId(@PathVariable("examPeriodId") String examPeriodId, @PathVariable("userId") String userId) {
        examService.deleteByUserId(examPeriodId, userId);
        return new ResultBean();
    }

    @ApiOperation(value = "通知", notes = "根据考试id通知")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "考试ID", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/notice/{id}", method = RequestMethod.POST)
    public ResultBean notice(@PathVariable("id") String id) {
        //查询考试信息
        ExamInfo info = examService.get(id);
        //发短信
        MessageUtil.sendMessage("活动验证", "SMS_139976476", "{\"name\":\"" + info.getRealName() + "\",\"address\":\"" + info.getAddress() + "\",\"exam\":\"" + info.getPeriod() + "\",\"system\":\"【吉林省安全人员考试平台】\"}", info.getPhone());
        //改为已通知
        examService.changeIsInform(id, 1);
        return new ResultBean();
    }
}
