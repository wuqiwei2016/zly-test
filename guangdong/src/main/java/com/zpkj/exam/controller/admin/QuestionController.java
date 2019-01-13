package com.zpkj.exam.controller.admin;

import com.zpkj.exam.common.ResultBean;
import com.zpkj.exam.domain.ExamItemInfo;
import com.zpkj.exam.domain.form.AttachmentInfoForm;
import com.zpkj.exam.domain.form.QuestionJudgeInfoForm;
import com.zpkj.exam.domain.form.QuestionMultipleInfoForm;
import com.zpkj.exam.domain.form.QuestionSingleInfoForm;
import com.zpkj.exam.domain.query.QuestionJudgeInfoQuery;
import com.zpkj.exam.domain.query.QuestionMultipleInfoQuery;
import com.zpkj.exam.domain.query.QuestionSingleInfoQuery;
import com.zpkj.exam.service.QuestionJudgeInfoService;
import com.zpkj.exam.service.QuestionMultipleInfoService;
import com.zpkj.exam.service.QuestionSingleInfoService;
import com.zpkj.exam.service.UploadService;
import com.zpkj.exam.service.impl.UploadServiceImpl;
import com.zpkj.exam.util.ExcelUtils;
import com.zpkj.exam.util.ValidateUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by wanshipeng on 2018/6/12.
 */
@RestController
@RequestMapping("/m/question")
@CrossOrigin
public class QuestionController {

    @Autowired
    QuestionSingleInfoService singleInfoService;
    @Autowired
    QuestionMultipleInfoService multipleInfoService;
    @Autowired
    QuestionJudgeInfoService judgeInfoService;
    @Autowired
    UploadService uploadService;

    @ApiOperation(value = "获取单选题列表", notes = "")
    @RequestMapping(value = "/single", method = RequestMethod.GET)
    public ResultBean getQuestionSingleInfoList(@ModelAttribute QuestionSingleInfoQuery query) {
        return new ResultBean(singleInfoService.findByPage(query));
    }

    @ApiOperation(value = "创建单选题", notes = "根据QuestionSingleInfo对象创建单选题")
    @ApiImplicitParam(name = "questionForm", value = "单选题详细实体QuestionSingleInfoForm", required = true, dataType = "QuestionSingleInfoForm")
    @RequestMapping(value = "/single", method = RequestMethod.POST)
    public ResultBean postQuestionSingleInfo(QuestionSingleInfoForm questionForm) {
        singleInfoService.add(questionForm);
        return new ResultBean();
    }

    @ApiOperation(value = "获取单选题详细信息", notes = "根据url的id来获取单选题详细信息")
    @ApiImplicitParam(name = "id", value = "单选题ID", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value = "/single/{id}", method = RequestMethod.GET)
    public ResultBean getQuestionSingleInfoById(@PathVariable("id") String id) {
        return new ResultBean(singleInfoService.get(id));
    }

    @ApiOperation(value = "更新单选题详细信息", notes = "根据url的id来指定更新对象，并根据传过来的question信息来更新单选题详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "单选题ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "questionForm", value = "单选题详细实体QuestionSingleInfoForm", required = true, dataType = "QuestionSingleInfoForm")
    })
    @RequestMapping(value = "/single/{id}", method = RequestMethod.PUT)
    public ResultBean updateQuestionSingleInfo(@PathVariable("id") String id, QuestionSingleInfoForm questionform) {
        singleInfoService.update(questionform);
        return new ResultBean();
    }

    @ApiOperation(value = "删除单选题", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "单选题ID", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value = "/single/{id}", method = RequestMethod.DELETE)
    public ResultBean deleteQuestionSingleInfo(@PathVariable("id") String id) {
        singleInfoService.delete(id);
        return new ResultBean();
    }

    @ApiOperation(value = "获取多选题列表", notes = "")
    @RequestMapping(value = "/multiple", method = RequestMethod.GET)
    public ResultBean getQuestionMultipleInfoList(@ModelAttribute QuestionMultipleInfoQuery query) {
        return new ResultBean(multipleInfoService.findByPage(query));
    }

    @ApiOperation(value = "创建多选题", notes = "根据QuestionMultipleInfo对象创建多选题")
    @ApiImplicitParam(name = "questionForm", value = "多选题详细实体QuestionMultipleInfoForm", required = true, dataType = "QuestionMultipleInfoForm")
    @RequestMapping(value = "/multiple", method = RequestMethod.POST)
    public ResultBean postQuestionMultipleInfo(QuestionMultipleInfoForm questionForm) {
        multipleInfoService.add(questionForm);
        return new ResultBean();
    }

    @ApiOperation(value = "获取多选题详细信息", notes = "根据url的id来获取多选题详细信息")
    @ApiImplicitParam(name = "id", value = "多选题ID", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value = "/multiple/{id}", method = RequestMethod.GET)
    public ResultBean getQuestionMultipleInfoById(@PathVariable("id") String id) {
        return new ResultBean(multipleInfoService.get(id));
    }

    @ApiOperation(value = "更新多选题详细信息", notes = "根据url的id来指定更新对象，并根据传过来的question信息来更新多选题详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "多选题ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "questionForm", value = "多选题详细实体QuestionMultipleInfoForm", required = true, dataType = "QuestionMultipleInfoForm")
    })
    @RequestMapping(value = "/multiple/{id}", method = RequestMethod.PUT)
    public ResultBean updateQuestionMultipleInfo(@PathVariable("id") String id, QuestionMultipleInfoForm questionform) {
        multipleInfoService.update(questionform);
        return new ResultBean();
    }

    @ApiOperation(value = "删除多选题", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "多选题ID", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value = "/multiple/{id}", method = RequestMethod.DELETE)
    public ResultBean deleteQuestionMultipleInfo(@PathVariable("id") String id) {
        multipleInfoService.delete(id);
        return new ResultBean();
    }

    @ApiOperation(value = "获取判断题列表", notes = "")
    @RequestMapping(value = "/judge", method = RequestMethod.GET)
    public ResultBean getQuestionJudgeInfoList(@ModelAttribute QuestionJudgeInfoQuery query) {
        return new ResultBean(judgeInfoService.findByPage(query));
    }

    @ApiOperation(value = "创建判断题", notes = "根据QuestionJudgeInfo对象创建判断题")
    @ApiImplicitParam(name = "questionForm", value = "判断题详细实体QuestionJudgeInfoForm", required = true, dataType = "QuestionJudgeInfoForm")
    @RequestMapping(value = "/judge", method = RequestMethod.POST)
    public ResultBean postQuestionJudgeInfo(QuestionJudgeInfoForm questionForm) {
        judgeInfoService.add(questionForm);
        return new ResultBean();
    }

    @ApiOperation(value = "获取判断题详细信息", notes = "根据url的id来获取判断题详细信息")
    @ApiImplicitParam(name = "id", value = "判断题ID", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value = "/judge/{id}", method = RequestMethod.GET)
    public ResultBean getQuestionJudgeInfoById(@PathVariable("id") String id) {
        return new ResultBean(judgeInfoService.get(id));
    }

    @ApiOperation(value = "更新判断题详细信息", notes = "根据url的id来指定更新对象，并根据传过来的question信息来更新判断题详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "判断题ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "questionForm", value = "判断题详细实体QuestionJudgeInfoForm", required = true, dataType = "QuestionJudgeInfoForm")
    })
    @RequestMapping(value = "/judge/{id}", method = RequestMethod.PUT)
    public ResultBean updateQuestionJudgeInfo(@PathVariable("id") String id, QuestionJudgeInfoForm questionform) {
        judgeInfoService.update(questionform);
        return new ResultBean();
    }

    @ApiOperation(value = "删除判断题", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "判断题ID", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value = "/judge/{id}", method = RequestMethod.DELETE)
    public ResultBean deleteQuestionJudgeInfo(@PathVariable("id") String id) {
        judgeInfoService.delete(id);
        return new ResultBean();
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

    @ApiOperation(value = "导入单选题库", notes = "")
    @RequestMapping(value = "/single/export", method = RequestMethod.POST)
    public ResultBean exportQuestionSingleInfo(@RequestParam("file") MultipartFile file, final AttachmentInfoForm form, final String questionType) throws Exception {
        uploadService.addFile(file, form);
        List<Map<Integer, String>> list = ExcelUtils.readExcel(getUploadPath() + form.getFilePath(), 8);
        String[] columnName = {"题干", "选项A", "选项B",
                "选项C", "选项D", "正确答案", "专业", "学员类型"};
        Map<Integer, String> m = list.get(0);
        StringBuffer sb = new StringBuffer("模板列名");
        boolean flag = false;
        for (int i = 0; i < 8; i++) {
            if (!columnName[i].equals(m.get(i))) {
                flag = true;
                sb.append("【" + columnName[i] + "】");
            }
        }
        sb.append("错误，请核对");
        if (flag) {
            return new ResultBean(1, sb.toString());
        }
        list.remove(0);
        Integer rightNum = 0;
        Integer errorNum = 0;
        QuestionSingleInfoForm questionForm = new QuestionSingleInfoForm();
        List<Map<Integer, String>> errorList = new ArrayList<Map<Integer, String>>();
        for (Map<Integer, String> map : list) {
            //循环插入题库
            questionForm.setId(UUID.randomUUID().toString().replace("-", ""));
            questionForm.setTitle(map.get(0));
            questionForm.setAnswerA(map.get(1));
            if (map.get(1).equals("") || map.get(1)==null){
                return new ResultBean(0,map.get(0)+"选项不能为空");
            }
            questionForm.setAnswerB(map.get(2));
            if (map.get(2).equals("") || map.get(2)==null){
                return new ResultBean(0,map.get(0)+"选项不能为空");
            }
            questionForm.setAnswerC(map.get(3));
            if (map.get(3).equals("") || map.get(3)==null){
                return new ResultBean(0,map.get(0)+"选项不能为空");
            }
            questionForm.setAnswerD(map.get(4));
            if (map.get(4).equals("") || map.get(4)==null){
                return new ResultBean(0,map.get(0)+"选项不能为空");
            }
            questionForm.setRightAnswer(map.get(5));
            if (!"ABCD".contains(questionForm.getRightAnswer())){
                return new ResultBean(0,"正确答案字母为大写");
            }
            //校验格式
            List<String> msgs = ValidateUtil.validate(questionForm);
            StringBuffer msg = new StringBuffer();
            msgs.forEach(row -> {
                msg.append(row + ",");
            });
        }
        //查询是否有重复题目
        String temp = "";
        for (int i = 0; i < list.size() - 1; i++)
        {
            temp = list.get(i).get(0);
            for (int j = i + 1; j < list.size(); j++)
            {
                String title = list.get(j).get(0);
                if (temp.equals(list.get(j).get(0)))
                {
                    return new ResultBean(0,"第" + (i + 1) + "个跟第" + (j + 1) + "个重复，值是：" + temp);
                }
            }
        }
        for (Map<Integer, String> map : list) {
            //循环插入题库
            questionForm.setId(UUID.randomUUID().toString().replace("-", ""));
            questionForm.setQuestionType(questionType);
            questionForm.setTitle(map.get(0));
            questionForm.setAnswerA(map.get(1));
            questionForm.setAnswerB(map.get(2));
            questionForm.setAnswerC(map.get(3));
            questionForm.setAnswerD(map.get(4));
            questionForm.setRightAnswer(map.get(5));
            questionForm.setPeopleType1(map.get(6));
            questionForm.setPeopleType2(map.get(7));
            //校验格式
            List<String> msgs = ValidateUtil.validate(questionForm);
            StringBuffer msg = new StringBuffer();
            msgs.forEach(row -> {
                msg.append(row + ",");
            });
            //判断状态
            if (msg.length() > 0) {
                map.put(8, msg.toString());
                errorList.add(map);
                errorNum++;
            } else {
                singleInfoService.add(questionForm);
                rightNum++;
            }
        }
        return new ResultBean(0, "导入成功【" + rightNum + "】道，失败【" + errorNum + "】道", errorList);
    }

    @ApiOperation(value = "导入多选题库", notes = "")
    @RequestMapping(value = "/multiple/export", method = RequestMethod.POST)
    public ResultBean exportQuestionMultipleInfo(@RequestParam("file") MultipartFile file, final AttachmentInfoForm form, final String questionType) throws Exception {
        uploadService.addFile(file, form);
        List<Map<Integer, String>> list = ExcelUtils.readExcel(getUploadPath() + form.getFilePath(), 8);
        String[] columnName = {"题干", "选项A", "选项B",
                "选项C", "选项D", "正确答案", "专业", "学员类型"};
        Map<Integer, String> m = list.get(0);
        StringBuffer sb = new StringBuffer("模板列名");
        boolean flag = false;
        for (int i = 0; i < 8; i++) {
            if (!columnName[i].equals(m.get(i))) {
                flag = true;
                sb.append("【" + columnName[i] + "】");
            }
        }
        sb.append("错误，请核对");
        if (flag) {
            return new ResultBean(1, sb.toString());
        }
        list.remove(0);
        Integer rightNum = 0;
        Integer errorNum = 0;
        QuestionMultipleInfoForm questionForm = new QuestionMultipleInfoForm();
        List<Map<Integer, String>> errorList = new ArrayList<Map<Integer, String>>();
        for (Map<Integer, String> map : list) {
            //循环插入题库
            questionForm.setId(UUID.randomUUID().toString().replace("-", ""));
            questionForm.setTitle(map.get(0));
            questionForm.setAnswerA(map.get(1));
            if (map.get(1).equals("") || map.get(1)==null){
                return new ResultBean(0,map.get(0)+"选项不能为空");
            }
            questionForm.setAnswerB(map.get(2));
            if (map.get(2).equals("") || map.get(2)==null){
                return new ResultBean(0,map.get(0)+"选项不能为空");
            }
            questionForm.setAnswerC(map.get(3));
            if (map.get(3).equals("") || map.get(3)==null){
                return new ResultBean(0,map.get(0)+"选项不能为空");
            }
            questionForm.setAnswerD(map.get(4));
            if (map.get(4).equals("") || map.get(4)==null){
                return new ResultBean(0,map.get(0)+"选项不能为空");
            }
            questionForm.setRightAnswer(map.get(5));
            int length = questionForm.getRightAnswer().length();
            for (int i = 0; i < length; i++) {
                char c = questionForm.getRightAnswer().charAt(i);
                String s = String.valueOf(c);
                if (!"ABCD".contains(s)){
                    return new ResultBean(0,"正确答案字母为大写");
                }
            }
            //校验格式
            List<String> msgs = ValidateUtil.validate(questionForm);
            StringBuffer msg = new StringBuffer();
            msgs.forEach(row -> {
                msg.append(row + ",");
            });
        }
        //查询是否有重复题目
        String temp = "";
        for (int i = 0; i < list.size() - 1; i++)
        {
            temp = list.get(i).get(0);
            for (int j = i + 1; j < list.size(); j++)
            {
                String title = list.get(j).get(0);
                if (temp.equals(list.get(j).get(0)))
                {
                    return new ResultBean(0,"第" + (i + 1) + "个跟第" + (j + 1) + "个重复，值是：" + temp);
                }
            }
        }
        for (Map<Integer, String> map : list) {
            //循环插入题库
            questionForm.setId(UUID.randomUUID().toString().replace("-", ""));
            questionForm.setQuestionType(questionType);
            questionForm.setTitle(map.get(0));
            questionForm.setAnswerA(map.get(1));
            questionForm.setAnswerB(map.get(2));
            questionForm.setAnswerC(map.get(3));
            questionForm.setAnswerD(map.get(4));
            questionForm.setRightAnswer(map.get(5));
            questionForm.setPeopleType1(map.get(6));
            questionForm.setPeopleType2(map.get(7));
            //校验格式
            List<String> msgs = ValidateUtil.validate(questionForm);
            StringBuffer msg = new StringBuffer();
            msgs.forEach(row -> {
                msg.append(row + ",");
            });
            //判断状态
            if (msg.length() > 0) {
                map.put(8, msg.toString());
                errorNum++;
                errorList.add(map);
            } else {
                multipleInfoService.add(questionForm);
                rightNum++;
            }
        }
        return new ResultBean(0, "导入成功【" + rightNum + "】道，失败【" + errorNum + "】道", errorList);
    }

    @ApiOperation(value = "导入判断题库", notes = "")
    @RequestMapping(value = "/judge/export", method = RequestMethod.POST)
    public ResultBean exportQuestionJudgeInfo(@RequestParam("file") MultipartFile file, final AttachmentInfoForm form, final String questionType) throws Exception {
        uploadService.addFile(file, form);
        List<Map<Integer, String>> list = ExcelUtils.readExcel(getUploadPath() + form.getFilePath(), 4);
        String[] columnName = {"题干", "正确答案", "专业", "学员类型"};
        Map<Integer, String> m = list.get(0);
        StringBuffer sb = new StringBuffer("模板列名");
        boolean flag = false;
        for (int i = 0; i < 4; i++) {
            if (!columnName[i].equals(m.get(i))) {
                flag = true;
                sb.append("【" + columnName[i] + "】");
            }
        }
        sb.append("错误，请核对");
        if (flag) {
            return new ResultBean(1, sb.toString());
        }
        list.remove(0);
        Integer rightNum = 0;
        Integer errorNum = 0;
        QuestionJudgeInfoForm questionForm = new QuestionJudgeInfoForm();
        List<Map<Integer, String>> errorList = new ArrayList<Map<Integer, String>>();
        for (Map<Integer, String> map : list) {
            //循环插入题库
            questionForm.setId(UUID.randomUUID().toString().replace("-", ""));
            questionForm.setTitle(map.get(0));
            questionForm.setRightAnswer(map.get(1));
            if (map.get(1).equals("") || map.get(1)==null){
                return new ResultBean(0,map.get(0)+"选项不能为空");
            }
            if (!"ABCD".contains(questionForm.getRightAnswer())){
                return new ResultBean(0,"正确答案字母为大写");
            }
            //校验格式
            List<String> msgs = ValidateUtil.validate(questionForm);
            StringBuffer msg = new StringBuffer();
            msgs.forEach(row -> {
                msg.append(row + ",");
            });
            //查询是否有重复题目
            String temp = "";
            for (int i = 0; i < list.size() - 1; i++)
            {
                temp = list.get(i).get(0);
                for (int j = i + 1; j < list.size(); j++)
                {
                    String title = list.get(j).get(0);
                    if (temp.equals(list.get(j).get(0)))
                    {
                        return new ResultBean(0,"第" + (i + 1) + "个跟第" + (j + 1) + "个重复，值是：" + temp);
                    }
                }
            }
        }
        for (Map<Integer, String> map : list) {
            //循环插入题库
            questionForm.setId(UUID.randomUUID().toString().replace("-", ""));
            questionForm.setQuestionType(questionType);
            questionForm.setTitle(map.get(0));
            questionForm.setRightAnswer(map.get(1));
            questionForm.setPeopleType1(map.get(2));
            questionForm.setPeopleType2(map.get(3));
            //校验格式
            List<String> msgs = ValidateUtil.validate(questionForm);
            StringBuffer msg = new StringBuffer();
            msgs.forEach(row -> {
                msg.append(row + ",");
            });
            //判断状态
            if (msg.length() > 0) {
                map.put(4, msg.toString());
                errorNum++;
                errorList.add(map);
            } else {
                judgeInfoService.add(questionForm);
                rightNum++;
            }
        }
        return new ResultBean(0, "导入成功【" + rightNum + "】道，失败【" + errorNum + "】道", errorList);
    }
}
