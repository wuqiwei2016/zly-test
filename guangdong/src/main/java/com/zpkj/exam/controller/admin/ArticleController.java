package com.zpkj.exam.controller.admin;

import com.zpkj.exam.common.ResultBean;
import com.zpkj.exam.domain.form.ArticleInfoForm;
import com.zpkj.exam.domain.query.ArticleInfoQuery;
import com.zpkj.exam.service.ArticleInfoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wanshipeng on 2018/6/12.
 */
@RestController
@RequestMapping("/m/article")
@CrossOrigin
public class ArticleController {

    @Autowired
    ArticleInfoService articleInfoService;

    @ApiOperation(value = "获取文章列表", notes = "")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResultBean getArticleInfoList(@ModelAttribute ArticleInfoQuery query) {
        return new ResultBean(articleInfoService.findByPage(query));
    }

    @ApiOperation(value = "创建文章", notes = "创建文章")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResultBean postArticleInfo(ArticleInfoForm articleInfoForm) {
        articleInfoService.add(articleInfoForm);
        return new ResultBean();
    }

    @ApiOperation(value = "获取文章详细信息", notes = "根据url的id来获取文章详细信息")
    @ApiImplicitParam(name = "id", value = "文章ID", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResultBean getArticleInfoById(@PathVariable("id") String id) {
        return new ResultBean(articleInfoService.get(id));
    }

    @ApiOperation(value = "更新文章详细信息", notes = "根据url的id来指定更新对象，并根据传过来的articleInfo信息来更新文章详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "文章ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "articleInfoForm", value = "文章详细实体ArticleInfoform", required = true, dataType = "ArticleInfoform")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResultBean updateArticleInfo(@PathVariable("id") String id, ArticleInfoForm articleInfoform) {
        articleInfoService.update(articleInfoform);
        return new ResultBean();
    }

    @ApiOperation(value = "删除文章", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "文章ID", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResultBean deleteArticleInfo(@PathVariable("id") String id) {
        articleInfoService.delete(id);
        return new ResultBean();
    }
}
