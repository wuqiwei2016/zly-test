package com.zpkj.exam.controller.fore;

import java.sql.Timestamp;
import com.zpkj.exam.common.ResultBean;
import com.zpkj.exam.dao.ExamInfoDao;
import com.zpkj.exam.domain.*;
import com.zpkj.exam.domain.form.*;
import com.zpkj.exam.domain.query.ExamApplyInfoQuery;
import com.zpkj.exam.domain.query.ExamItemInfoQuery;
import com.zpkj.exam.domain.query.PermissionInfoQuery;
import com.zpkj.exam.domain.query.SpecialExamineeInfoQuery;
import com.zpkj.exam.filter.XssFilter;
import com.zpkj.exam.service.*;
import com.zpkj.exam.util.JWTUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wanshipeng on 2018/6/12.
 */
@RestController
@RequestMapping("/f/user")
@CrossOrigin
public class ForeUserController {

    @Autowired
    UserBaseInfoService userBaseInfoService;
    @Autowired
    UserLoginInfoService userLoginInfoService;
    @Autowired
    PermissionInfoService permissionInfoService;
    @Autowired
    ExamInfoService examInfoService;
    @Autowired
    SpecialExamineeInfoService specialExamineeInfoService;
    @Autowired
    ExamItemInfoService examItemInfoService;
    @Autowired
    ExamInfoService examService;
    @Autowired
    ExamApplyInfoService examApplyInfoService;
    @Autowired
    ArticleInfoService articleInfoService;
    @Autowired
    AttachmentInfoService attachmentInfoService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ForeUserController.class);
    @ApiOperation(value = "创建用户", notes = "创建用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "realName", value = "真实姓名", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "headImg", value = "头像", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "idCard", value = "身份证号", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "sex", value = "性别", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "birthday", value = "生日", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "电话", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "email", value = "邮箱", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "province", value = "省", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "city", value = "市", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "area", value = "区", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "address", value = "地址", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "education", value = "学历", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "workUnit", value = "工作单位", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "postCode", value = "邮政编码", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "fixedTelephone", value = "固定电话", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "peopleType", value = "人员类型", paramType = "query", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "annexUrl", value = "附件地址", paramType = "query", required = true, dataType = "String")
    })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResultBean postUserLoginInfo(@ApiIgnore UserBaseInfoForm userBaseInfoForm) {
        LOGGER.info("start 当前用户的出生日期为"+userBaseInfoForm.getBirthday());
        //创建考生信息
//        if (userBaseInfoForm.getBirthday()!=null && !userBaseInfoForm.getBirthday().equals("")){
//            LOGGER.info("有值 开始进行添加数据库");
//            Timestamp ts = Timestamp.valueOf(userBaseInfoForm.getBirthday()+" 00:00:00");
//            userBaseInfoForm.setBirthdays(ts);
//        }else {
//            LOGGER.info("after 开始进行转换");
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String time = df.format(new Date());
//            Timestamp ts = Timestamp.valueOf(time);
//            LOGGER.info("转换后的格式为"+ts);
//            userBaseInfoForm.setBirthdays(ts);
//        }
        userBaseInfoService.add(userBaseInfoForm);
        //创建用户登录信息
        UserLoginInfoForm form = new UserLoginInfoForm();
        form.setAccount(userBaseInfoForm.getPhone());
        form.setAccountType(1);
        form.setUserId(userBaseInfoForm.getId());
        form.setHeadImg(userBaseInfoForm.getHeadImg());
        form.setPeopleType(userBaseInfoForm.getPeopleType());
        form.setAnnexUrl(userBaseInfoForm.getAnnexUrl());
        userLoginInfoService.add(form);
        //创建权限信息
        PermissionInfoForm permissionInfoForm = new PermissionInfoForm();
        permissionInfoForm.setUserId(userBaseInfoForm.getId());
        permissionInfoForm.setUserType("3");
        permissionInfoService.add(permissionInfoForm);
        Map map = new HashMap<String, Object>();
        map.put("loginId", form.getId());
        map.put("userId", userBaseInfoForm.getId());
        String accessToken = JWTUtil.createJWT(UUID.randomUUID().toString(), map, 24 * 60 * 60 * 1000);
        return new ResultBean(0, accessToken);
    }

    @ApiOperation(value = "获取用户详细信息", notes = "")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResultBean getUserLoginInfoById(String accessToken) {
        Claims claims = JWTUtil.parseJWT(accessToken);
        String userId = (String) claims.get("userId");
        UserBaseInfo userBaseInfo = userBaseInfoService.get(userId);
        //查询文件名称
        AttachmentInfo attachmentInfo = attachmentInfoService.get(userBaseInfo.getAnnexUrl());
        if(attachmentInfo==null){
            userBaseInfo.setAnnexName("");
        }else {
            userBaseInfo.setAnnexName(attachmentInfo.getAttachName());
        }
        return new ResultBean(userBaseInfo);
    }

    @ApiOperation(value = "更新用户详细信息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "realName", value = "真实姓名", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "headImg", value = "头像", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "idCard", value = "身份证号", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "sex", value = "性别", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "birthday", value = "生日", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "电话", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "email", value = "邮箱", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "province", value = "省", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "city", value = "市", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "area", value = "区", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "address", value = "地址", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "education", value = "学历", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "workUnit", value = "工作单位", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "postCode", value = "邮政编码", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "fixedTelephone", value = "固定电话", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "peopleType", value = "人员类型", paramType = "query", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "annexUrl", value = "附件地址", paramType = "query", required = true, dataType = "String")
    })
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResultBean updateUserLoginInfo(String accessToken, @ApiIgnore UserBaseInfoForm userBaseInfoForm) {
        Claims claims = JWTUtil.parseJWT(accessToken);
        String userId = (String) claims.get("userId");
        LOGGER.info("start 当前用户的出生日期为"+userBaseInfoForm.getBirthday());
//        if (userBaseInfoForm.getBirthday()!=null && !userBaseInfoForm.getBirthday().equals("")){
//            Timestamp ts = Timestamp.valueOf(userBaseInfoForm.getBirthday()+" 00:00:00");
//            userBaseInfoForm.setBirthdays(ts);
//        }else {
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String time = df.format(new Date());
//            Timestamp ts = Timestamp.valueOf(time);
//            LOGGER.info("转换后的格式为"+ts);
//            userBaseInfoForm.setBirthdays(ts);
//        }
        userBaseInfoForm.setId(userId);
        userBaseInfoService.update(userBaseInfoForm);
        //userLoginInfoService.updateHeadImgByUserId(userBaseInfoForm.getHeadImg(),userBaseInfoForm.get, userId);
        UserLoginInfoForm form = new UserLoginInfoForm();
        form.setUserId(userId);
        form.setHeadImg(userBaseInfoForm.getHeadImg());
        form.setPeopleType(userBaseInfoForm.getPeopleType());
        form.setAnnexUrl(userBaseInfoForm.getAnnexUrl());
        userLoginInfoService.updateLoginByUserId(form);
        return new ResultBean();
    }

    @ApiOperation(value = "获取用户菜单", notes = "")
    @RequestMapping(value = "/getMenu", method = RequestMethod.GET)
    public ResultBean getMenu(String accessToken) {
        Map<String, Object> home = new HashMap<>();
        home.put("title", "主页");
        home.put("icon", "layui-icon-home");
        home.put("jump", "/");

        Map<String, Object> student = new HashMap<>();
        List list1 = new ArrayList<>();
        Map<String, Object> studentList = new HashMap<>();
        studentList.put("name", "list");
        studentList.put("title", "考生列表");
        studentList.put("jump", "studentInfo/list");
        Map<String, Object> scoreList = new HashMap<>();
        scoreList.put("name", "list");
        scoreList.put("title", "模拟考试记录");
        scoreList.put("jump", "studentInfo/studentlist");
        list1.add(studentList);
        list1.add(scoreList);
        student.put("name", "studentInfo");
        student.put("title", "考生管理");
        student.put("icon", "layui-icon-app");
        student.put("list", list1);
//        Map<String, Object> scoreleList = new HashMap<>();
//        student.put("name", "studentInfo");
//        student.put("title", "考生成绩管理");
//        student.put("icon", "layui-icon-app");
//        student.put("list", list1);

        Map<String, Object> examPeriod = new HashMap<>();
        List list2 = new ArrayList<>();
        Map<String, Object> examPeriodList = new HashMap<>();
        examPeriodList.put("name", "list");
        examPeriodList.put("title", "模拟考试期次列表");
        examPeriodList.put("jump", "examPeriod/list");
        list2.add(examPeriodList);
        examPeriod.put("name", "examPeriod");
        examPeriod.put("title", "模拟考试期次管理");
        examPeriod.put("icon", "layui-icon-app");
        examPeriod.put("list", list2);

        Map<String, Object> question = new HashMap<>();
        List list3 = new ArrayList<>();
        Map<String, Object> singleList = new HashMap<>();
        singleList.put("name", "list");
        singleList.put("title", "单选题");
        singleList.put("jump", "question/single/list");
        Map<String, Object> multipleList = new HashMap<>();
        multipleList.put("name", "list");
        multipleList.put("title", "多选题");
        multipleList.put("jump", "question/multiple/list");
        Map<String, Object> judgeList = new HashMap<>();
        judgeList.put("name", "list");
        judgeList.put("title", "判断题");
        judgeList.put("jump", "question/judge/list");
        list3.add(singleList);
        list3.add(multipleList);
        list3.add(judgeList);
        question.put("name", "question");
        question.put("title", "练习题库管理");
        question.put("icon", "layui-icon-template");
        question.put("list", list3);

        Map<String, Object> question2 = new HashMap<>();
        List list32 = new ArrayList<>();
        Map<String, Object> singleList2 = new HashMap<>();
        singleList2.put("name", "list");
        singleList2.put("title", "单选题");
        singleList2.put("jump", "question2/single/list");
        Map<String, Object> multipleList2 = new HashMap<>();
        multipleList2.put("name", "list");
        multipleList2.put("title", "多选题");
        multipleList2.put("jump", "question2/multiple/list");
        Map<String, Object> judgeList2 = new HashMap<>();
        judgeList2.put("name", "list");
        judgeList2.put("title", "判断题");
        judgeList2.put("jump", "question2/judge/list");
        list32.add(singleList2);
        list32.add(multipleList2);
        list32.add(judgeList2);
        question2.put("name", "question");
        question2.put("title", "模拟考试题库管理");
        question2.put("icon", "layui-icon-template");
        question2.put("list", list32);

        Map<String, Object> arcitle = new HashMap<>();
        List list4 = new ArrayList<>();
        Map<String, Object> noticeList = new HashMap<>();
        noticeList.put("name", "list");
        noticeList.put("title", "公告");
        noticeList.put("jump", "notice/list");
        Map<String, Object> newsList = new HashMap<>();
        newsList.put("name", "list");
        newsList.put("title", "新闻");
        newsList.put("jump", "news/list");
        Map<String, Object> carouselList = new HashMap<>();
        carouselList.put("name", "list");
        carouselList.put("title", "轮播图");
        carouselList.put("jump", "carousel/list");
        list4.add(noticeList);
        list4.add(newsList);
        list4.add(carouselList);
        arcitle.put("name", "arcitle");
        arcitle.put("title", "发布管理");
        arcitle.put("icon", "layui-icon-template");
        arcitle.put("list", list4);

        Map<String, Object> user = new HashMap<>();
        List list5 = new ArrayList<>();
        Map<String, Object> userList = new HashMap<>();
        userList.put("name", "administrators-list");
        userList.put("title", "政府管理员");
        userList.put("jump", "user/list");
        list5.add(userList);
        user.put("name", "user");
        user.put("title", "用户管理");
        user.put("icon", "layui-icon-user");
        user.put("list", list5);

        Map<String, Object> special = new HashMap<>();
        special.put("name", "special");
        special.put("title", "特殊考生管理");
        special.put("icon", "layui-icon-auz");
        special.put("jump", "special/list");

        Map<String, Object> pay = new HashMap<>();
        pay.put("name", "pay");
        pay.put("title", "交费管理");
        pay.put("icon", "layui-icon-auz");
        pay.put("jump", "pay/list");

        Claims claims = JWTUtil.parseJWT(accessToken);
        String userId = (String) claims.get("userId");
        UserBaseInfo userBaseInfo = userBaseInfoService.get(userId);
        List menu = new ArrayList();
        if (userBaseInfo.getUserType().equals("1")) {
            //后台管理员
            menu.add(special);
            menu.add(pay);
        } else if (userBaseInfo.getUserType().equals("2")) {
            //政府管理员
            menu.add(home);
            String permission = userBaseInfo.getPermission();
            if (permission != null && !permission.equals("")) {
                if (permission.indexOf("1") >= 0) {
                    menu.add(student);
                }
                if (permission.indexOf("2") >= 0) {
                    menu.add(examPeriod);
                }
                if (permission.indexOf("3") >= 0) {
                    menu.add(question);
                    menu.add(question2);
                }
                /*if (permission.indexOf("4") >= 0) {
                    menu.add(arcitle);
                }*/
                if (permission.indexOf("5") >= 0) {
                    menu.add(user);
                }
                if (permission.indexOf("6") >= 0) {
                    menu.add(pay);
                }
            }
        }
        return new ResultBean(menu);
    }

    public ResultBean submit(String examId,Date EndTime) {
        int totalScore = 0;
        ExamInfo examInfo = examService.get(examId);
        if (examInfo.getEndTime() != null) {
            return new ResultBean(1, "已交卷，请勿重复提交");
        }
        //查询是否为特殊考生
        SpecialExamineeInfoQuery specialExamineeInfoQuery = new SpecialExamineeInfoQuery();
        specialExamineeInfoQuery.setRealName(examInfo.getRealName());
        specialExamineeInfoQuery.setIdCard(examInfo.getIdCard());
        specialExamineeInfoQuery.setPhone(examInfo.getPhone());
        if (specialExamineeInfoService.find(specialExamineeInfoQuery).size() == 0) {
            //查询试题
            ExamItemInfoQuery query = new ExamItemInfoQuery();
            query.setExamId(examId);
            List<ExamItemInfo> list = examItemInfoService.find(query);
            //改卷
            ExamItemInfoForm form = new ExamItemInfoForm();
            for (ExamItemInfo item : list) {
                form.setId(item.getId());
                if (item.getRightAnswer().equals(item.getUserAnswer())) {
                    totalScore += 1;
                    form.setResult(1);
                } else {
                    form.setResult(2);
                }
                examItemInfoService.update(form);
            }
        } else {
            //是特殊考生，查询做题数量
            ExamItemInfoQuery query1 = new ExamItemInfoQuery();
            query1.setExamId(examId);
            query1.setUserAnswer(1 + "");
            List<ExamItemInfo> list1 = examItemInfoService.find(query1);
            int doNum = list1.size();
            if (doNum >= 90) {
                totalScore = new Random().nextInt(doNum) % (doNum - 90 + 1) + 90;
            } else {
                //查询试题
                ExamItemInfoQuery query = new ExamItemInfoQuery();
                query.setExamId(examId);
                List<ExamItemInfo> list = examItemInfoService.find(query);
                //改卷
                ExamItemInfoForm form = new ExamItemInfoForm();
                for (ExamItemInfo item : list) {
                    form.setId(item.getId());
                    if (item.getRightAnswer().equals(item.getUserAnswer())) {
                        totalScore += 1;
                        form.setResult(1);
                    } else {
                        form.setResult(2);
                    }
                    examItemInfoService.update(form);
                }
            }
        }
        //判分
        //ExamInfo examInfo = examService.get(examId);
        ExamInfoForm form1 = new ExamInfoForm();
        form1.setId(examId);
        form1.setStatus(2);
        form1.setEndTime(EndTime);
        form1.setTotalScore("" + totalScore);
//        //从session里面获取图片
//        HttpServletRequest httpServletRequest=null;
//        httpServletRequest.getSession().getAttribute("");
        if (totalScore >= 90) {
            form1.setResult(1);
            //生成考试通过公告
            ArticleInfoForm articleInfoForm = new ArticleInfoForm();
            articleInfoForm.setTitle("关于" + examInfo.getRealName() + "考试通过的公告");
            articleInfoForm.setArticleType("4");
            articleInfoForm.setOwnerId(examId);
            articleInfoForm.setReleaseTime(new Date());
            articleInfoService.add(articleInfoForm);
        } else {
            form1.setResult(2);
        }
        examService.update(form1);
        //修改申请为不可用
        ExamApplyInfoQuery query = new ExamApplyInfoQuery();
        query.setUserId(examInfo.getUserId());
        List<ExamApplyInfo> list = examApplyInfoService.find(query);
        if (list.size() > 0) {
            ExamApplyInfoForm form2 = new ExamApplyInfoForm();
            form2.setId(list.get(0).getId());
            form2.setExamId(examId);
            form2.setStatus(2);
            examApplyInfoService.update(form2);
        }
        return new ResultBean(0, "" + totalScore);
    }
}
