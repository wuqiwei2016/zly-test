package com.zpkj.exam.controller.fore;

import com.zpkj.exam.common.ResultBean;
import com.zpkj.exam.domain.*;
import com.zpkj.exam.domain.form.ArticleInfoForm;
import com.zpkj.exam.domain.form.ExamApplyInfoForm;
import com.zpkj.exam.domain.form.ExamInfoForm;
import com.zpkj.exam.domain.form.ExamItemInfoForm;
import com.zpkj.exam.domain.query.*;
import com.zpkj.exam.service.*;
import com.zpkj.exam.util.FileUtil;
import com.zpkj.exam.util.JWTUtil;
import com.zpkj.exam.util.MultipartFileUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by wanshipeng on 2018/6/12.
 */
@RestController
@RequestMapping("/f/exam")
@CrossOrigin
public class ForeExamController {

    @Autowired
    ExamInfoService examService;
    @Autowired
    ExamItemInfoService examItemInfoService;
    @Autowired
    QuestionSingleInfoService singleInfoService;
    @Autowired
    QuestionMultipleInfoService multipleInfoService;
    @Autowired
    QuestionJudgeInfoService judgeInfoService;
    @Autowired
    UserBaseInfoService userBaseInfoService;
    @Autowired
    ExamApplyInfoService examApplyInfoService;
    @Autowired
    SpecialExamineeInfoService specialExamineeInfoService;
    @Autowired
    ArticleInfoService articleInfoService;
    @Autowired
    private IncomeInfoService incomeInfoService;
    @Autowired
    private UploadService uploadService;

    @ApiOperation(value = "获取考试列表", notes = "")
    @ApiImplicitParam(name = "accessToken", value = "accessToken", required = true, paramType = "query", dataType = "String")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultBean getExamInfoList(String accessToken) {
        ExamInfoQuery query = new ExamInfoQuery();
        Claims claims = JWTUtil.parseJWT(accessToken);
        String userId = (String) claims.get("userId");
        query.setUserId(userId);
        return new ResultBean(examService.find(query));
    }

    @ApiOperation(value = "获取考试详情", notes = "")
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public ResultBean getResult(String examId) {
        ExamItemInfoQuery query = new ExamItemInfoQuery();
        query.setExamId(examId);
        ExamInfo examInfo = examService.get(examId);
        examInfo.setItems(examItemInfoService.find(query));
        return new ResultBean(examInfo);
    }

    @ApiOperation(value = "开始考试", notes = "")
    @ApiImplicitParam(name = "examId", value = "考试id", required = true, paramType = "query", dataType = "String")
    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public ResultBean startExam(String examId) {
        List<Integer> result = new ArrayList<Integer>();
        //判断考试是否可用
        ExamInfo examInfo = examService.get(examId);
        if (examInfo != null) {
            if (examInfo.getStartTime() != null) {
                //开始考试
                if (1 == examInfo.getStatus()) {
                    //没有提交试卷，判断提交时间
                    Date startTime = examInfo.getStartTime();
                    long now = new Date().getTime();
                    long start = startTime.getTime() + 1000 * 60 * 60;
                    if (now < start) {
                        //继续考试
                        ExamItemInfoQuery query = new ExamItemInfoQuery();
                        query.setExamId(examId);
                        List<ExamItemInfo> list = examItemInfoService.find(query);
                        for (ExamItemInfo item : list) {
                            if (item.getUserAnswer() != null && !item.getUserAnswer().equals("")) {
                                result.add(1);
                            } else {
                                result.add(2);
                            }
                        }
                        return new ResultBean(0, "" + (start - now) / 1000, result);
                    } else {
                        submit(examId);
                        return new ResultBean(1, "本场考试已结束");
                    }
                } else {
                    return new ResultBean(1, "本场考试已结束");
                }
            } else {
                //查询试题
                UserBaseInfo user = userBaseInfoService.get(examInfo.getUserId());
                //单选
                QuestionSingleInfoQuery query1 = new QuestionSingleInfoQuery();
                query1.setPeopleType1(user.getPeopleType1());
                query1.setPeopleType2(user.getPeopleType2());
                query1.setLimit(70);
                List<QuestionSingleInfo> list1 = singleInfoService.findByLimit(query1);
                //多选
                QuestionMultipleInfoQuery query2 = new QuestionMultipleInfoQuery();
                query2.setPeopleType1(user.getPeopleType1());
                query2.setPeopleType2(user.getPeopleType2());
                query2.setLimit(20);
                List<QuestionMultipleInfo> list2 = multipleInfoService.findByLimit(query2);
                //判断
                QuestionJudgeInfoQuery query3 = new QuestionJudgeInfoQuery();
                query3.setPeopleType1(user.getPeopleType1());
                query3.setPeopleType2(user.getPeopleType2());
                query3.setLimit(10);
                List<QuestionJudgeInfo> list3 = judgeInfoService.findByLimit(query3);
                //生成考试题项
                for (int i = 0; i < list1.size(); i++) {
                    QuestionSingleInfo item = list1.get(i);
                    ExamItemInfoForm form = new ExamItemInfoForm();
                    form.setType(1);
                    form.setOrderNum(i + 1);
                    form.setExamId(examId);
                    form.setQuestionId(item.getId());
                    form.setQuestionType(item.getQuestionType());
                    form.setTitle(item.getTitle());
                    form.setAnswerA(item.getAnswerA());
                    form.setAnswerB(item.getAnswerB());
                    form.setAnswerC(item.getAnswerC());
                    form.setAnswerD(item.getAnswerD());
                    form.setRightAnswer(item.getRightAnswer());
                    examItemInfoService.add(form);
                }
                for (int i = 0; i < list2.size(); i++) {
                    QuestionMultipleInfo item = list2.get(i);
                    ExamItemInfoForm form = new ExamItemInfoForm();
                    form.setType(2);
                    form.setOrderNum(i + 1 + 70);
                    form.setExamId(examId);
                    form.setQuestionId(item.getId());
                    form.setQuestionType(item.getQuestionType());
                    form.setTitle(item.getTitle());
                    form.setAnswerA(item.getAnswerA());
                    form.setAnswerB(item.getAnswerB());
                    form.setAnswerC(item.getAnswerC());
                    form.setAnswerD(item.getAnswerD());
                    form.setRightAnswer(item.getRightAnswer());
                    examItemInfoService.add(form);
                }
                for (int i = 0; i < list3.size(); i++) {
                    QuestionJudgeInfo item = list3.get(i);
                    ExamItemInfoForm form = new ExamItemInfoForm();
                    form.setType(3);
                    form.setOrderNum(i + 1 + 90);
                    form.setExamId(examId);
                    form.setQuestionId(item.getId());
                    form.setQuestionType(item.getQuestionType());
                    form.setTitle(item.getTitle());
                    form.setRightAnswer(item.getRightAnswer());
                    examItemInfoService.add(form);
                }
                for (int i = 0; i < 100; i++) {
                    result.add(2);
                }
                ExamInfoForm form = new ExamInfoForm();
                form.setId(examId);
                form.setStartTime(new Date());
                examService.update(form);
                return new ResultBean(0, "" + 60 * 60, result);
            }
        } else {
            return new ResultBean(1, "没有此考试");
        }
    }

    @ApiOperation(value = "提交考试试题答案", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "itemId", value = "考试项ID", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "answer", value = "答案", paramType = "query", required = true, dataType = "String")
    })
    @RequestMapping(value = "/submitQuestion", method = RequestMethod.POST)
    public ResultBean postExamInfo(String itemId, String answer) {
        examItemInfoService.changeAnswer(itemId, answer);
        return new ResultBean();
    }

    @ApiOperation(value = "获取试题", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examId", value = "考试ID", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "orderNum", value = "序号", paramType = "query", required = true, dataType = "int")
    })
    @RequestMapping(value = "/getQuestion", method = RequestMethod.POST)
    public ResultBean getQuestion(String examId, Integer orderNum) {
        ExamItemInfo itemInfo = examItemInfoService.getByExamIdAndOrderNum(examId, orderNum);
        itemInfo.setRightAnswer("");
        return new ResultBean(itemInfo);
    }


    @ApiOperation(value = "提交试卷", notes = "")
    @ApiImplicitParam(name = "examId", value = "考试id", paramType = "query", required = true, dataType = "String")
    @RequestMapping(value = "/submit", method = RequestMethod.GET)
    public ResultBean submit(String examId) {
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
        form1.setEndTime(new Date());
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

//    @ApiOperation(value = "提交抓拍图片", notes = "")
//    @ApiImplicitParam(name = "examId", value = "考试id", paramType = "query", required = true, dataType = "String")
//    @RequestMapping(value = "/picture", method = RequestMethod.GET)
//    public ResultBean picture(String examId, @RequestParam("file") MultipartFileUtil file, final AttachmentInfoForm form) {
//        ExamInfo examInfo = examService.get(examId);
//        ExamInfoForm form1 = new ExamInfoForm();
//        form1.setId(examId);
//        if (examInfo.getPicture1() == null || examInfo.getPicture1().equals("")) {
//            form1.setPicture1(form.getId());
//        } else if (examInfo.getPicture2() == null || examInfo.getPicture2().equals("")) {
//            form1.setPicture2(form.getId());
//        } else if (examInfo.getPicture3() == null || examInfo.getPicture3().equals("")) {
//            form1.setPicture2(form.getId());
//        }
//        //更新考试抓拍图片
//        examService.update(form1);
//        return new ResultBean();
//    }

    @ApiOperation(value = "查询是否申请考试", notes = "")
    @ApiImplicitParam(name = "accessToken", value = "accessToken", paramType = "query", required = true, dataType = "String")
    @RequestMapping(value = "/isApply", method = RequestMethod.GET)
    public ResultBean isApply(String accessToken) {
        Claims claims = JWTUtil.parseJWT(accessToken);
        String userId = (String) claims.get("userId");
        //查询是否交费
        IncomeInfoQuery query1 = new IncomeInfoQuery();
        query1.setUserBaseId(userId);
        List<IncomeInfo> incomeInfos = incomeInfoService.find(query1);
        if (incomeInfos.size() > 0) {
            IncomeInfo incomeInfo = incomeInfos.get(0);
            if (!"1".equals(incomeInfo.getStatus())) {
                return new ResultBean(2, "");
            }
        } else {
            return new ResultBean(2, "");
        }
        ExamInfoQuery query = new ExamInfoQuery();
        query.setUserId(userId);
        List<ExamInfo> examInfos = examService.find(query);
        if (examInfos.size() == 0) {
            return new ResultBean(1, "");
        } else {
            boolean flag = false;
            for (ExamInfo exam : examInfos) {
                if (1 == exam.getExamStatus() || 2 == exam.getExamStatus()) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                return new ResultBean(0, "");
            } else {
                return new ResultBean(1, "");
            }
        }
    }

    @ApiOperation(value = "申请考试", notes = "")
    @ApiImplicitParam(name = "accessToken", value = "accessToken", paramType = "query", required = true, dataType = "String")
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public ResultBean apply(String accessToken) {
        Claims claims = JWTUtil.parseJWT(accessToken);
        String userId = (String) claims.get("userId");
        ExamApplyInfoQuery query = new ExamApplyInfoQuery();
        query.setUserId(userId);
        query.setStatus(1);
        List<ExamApplyInfo> list = examApplyInfoService.find(query);
        if (list.size() > 0) {
            ExamApplyInfo applyInfo = list.get(0);
            if (applyInfo.getExamId() == null || applyInfo.getExamId().equals("")) {
                return new ResultBean(1, "已申请考试");
            } else {
                String examId = applyInfo.getExamId();
                ExamInfo info = examService.get(examId);
                if (info.getStatus() == 1) {
                    if (info.getExamStatus() == 3) {
                        ExamApplyInfoForm form = new ExamApplyInfoForm();
                        form.setUserId(userId);
                        form.setApplyTime(new Date());
                        examApplyInfoService.add(form);
                    }
                } else {
                    if (info.getResult() != null && info.getResult() == 2) {
                        ExamApplyInfoForm form = new ExamApplyInfoForm();
                        form.setUserId(userId);
                        form.setApplyTime(new Date());
                        examApplyInfoService.add(form);
                    }
                }
            }
        } else {
            ExamApplyInfoForm form = new ExamApplyInfoForm();
            form.setUserId(userId);
            form.setApplyTime(new Date());
            examApplyInfoService.add(form);
        }
        return new ResultBean();
    }

    /**
     * base64编码解析
     * 只存三张图片
     */
    @ApiOperation(value = "摄像头抓拍", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "imageFile", value = "图片上传", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "examId", value = "用户ID", paramType = "query", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/GenerateImage")
    public void GenerateImage(String imageFile, String examId) { // 对字节数组字符串进行Base64解码并生成图片
        try {
            MultipartFile multipartFile = MultipartFileUtil.base64ToMultipart(imageFile);
            String originalFilename = multipartFile.getOriginalFilename();   //原始文件名
            String suffix = FileUtil.getSuffix(originalFilename);   //文件后缀名
            String attachPath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("/yyyy/MM/dd/")) + UUID.randomUUID().toString();
            String realPath = getUploadPath() + attachPath;
            // ****上传文件****
            // 上传路径://真实路径/文件上传路径/日期/时间.后缀
            String targetPathNameWithoutSuffix = realPath;
            FileUtil.uploadFileSpringMVC(multipartFile, targetPathNameWithoutSuffix);
            String path = attachPath + "." + suffix;
            ExamInfo examInfo = examService.get(examId);
            ExamInfoForm form1 = new ExamInfoForm();
            form1.setId(examId);
            if (examInfo.getPicture1() == null && examInfo.getPicture2() == null && examInfo.getPicture3() == null) {
                form1.setPicture1(path);
            }
            if (examInfo.getPicture1() != null && examInfo.getPicture2() == null && examInfo.getPicture3() == null) {
                form1.setPicture2(path);
            }
            if (examInfo.getPicture1() != null && examInfo.getPicture2() != null && examInfo.getPicture3() == null) {
                form1.setPicture3(path);
            }
            //更新考试抓拍图片
            examService.update(form1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Value("${uploadPath.win}")
    private String winUploadPath;
    @Value("${uploadPath.linux}")
    private String linuxUploadPath;

    public String getUploadPath() {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            return winUploadPath;
        } else {
            return linuxUploadPath;
        }
    }

    @ApiOperation(value = "获取练习题", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "试题类型", paramType = "query", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/getTestQuestion", method = RequestMethod.POST)
    public ResultBean getTestQuestion(String type, String accessToken) {
        Claims claims = JWTUtil.parseJWT(accessToken);
        String userId = (String) claims.get("userId");
        UserBaseInfo user = userBaseInfoService.get(userId);

        ExamInfoQuery examInfoQuery = new ExamInfoQuery();
        examInfoQuery.setUserId(userId);
        List<ExamInfo> examInfos = examService.find(examInfoQuery);
        boolean flag = false;
        if (examInfos.size() != 0) {
            for (ExamInfo exam : examInfos) {
                if (1 == exam.getExamStatus() || 2 == exam.getExamStatus()) {
                    flag = true;
                    break;
                }
            }
        }
        if (!flag) {
            return new ResultBean(1, "没有做练习题权限");
        }

        if ("1".equals(type)) {
            QuestionSingleInfoQuery query = new QuestionSingleInfoQuery();
            return new ResultBean(singleInfoService.getRandom(query));
        } else if ("2".equals(type)) {
            QuestionMultipleInfoQuery query = new QuestionMultipleInfoQuery();
            return new ResultBean(multipleInfoService.getRandom(query));
        } else if ("3".equals(type)) {
            QuestionJudgeInfoQuery query = new QuestionJudgeInfoQuery();
            return new ResultBean(judgeInfoService.getRandom(query));
        } else {
            return new ResultBean(1, "请选择正确的练习题类型");
        }

    }
}
