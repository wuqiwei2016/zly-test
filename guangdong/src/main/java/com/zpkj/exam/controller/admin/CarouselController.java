package com.zpkj.exam.controller.admin;

import com.zpkj.exam.common.ResultBean;
import com.zpkj.exam.domain.form.CarouselInfoForm;
import com.zpkj.exam.domain.query.CarouselInfoQuery;
import com.zpkj.exam.service.CarouselInfoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wanshipeng on 2018/6/12.
 */
@RestController
@RequestMapping("/m/carousel")
@CrossOrigin
public class CarouselController {

    @Autowired
    CarouselInfoService carouselService;

    @ApiOperation(value = "获取轮播图列表", notes = "")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResultBean getCarouselInfoList(@ModelAttribute CarouselInfoQuery query) {
        return new ResultBean(carouselService.find(query));
    }

    @ApiOperation(value = "创建轮播图", notes = "根据CarouselInfo对象创建轮播图")
    @ApiImplicitParam(name = "carouselForm", value = "轮播图详细实体CarouselInfoForm", required = true, dataType = "CarouselInfoForm")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResultBean postCarouselInfo(CarouselInfoForm carouselForm) {
        carouselService.add(carouselForm);
        return new ResultBean();
    }

    @ApiOperation(value = "获取轮播图详细信息", notes = "根据url的id来获取轮播图详细信息")
    @ApiImplicitParam(name = "id", value = "轮播图ID", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResultBean getCarouselInfoById(@PathVariable("id") String id) {
        return new ResultBean(carouselService.get(id));
    }

    @ApiOperation(value = "更新轮播图详细信息", notes = "根据url的id来指定更新对象，并根据传过来的carousel信息来更新轮播图详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "轮播图ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "carouselForm", value = "轮播图详细实体CarouselInfoform", required = true, dataType = "CarouselInfoform")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResultBean updateCarouselInfo(@PathVariable("id") String id, CarouselInfoForm carouselform) {
        carouselService.update(carouselform);
        return new ResultBean();
    }

    @ApiOperation(value = "删除轮播图", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "轮播图ID", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResultBean deleteCarouselInfo(@PathVariable("id") String id) {
        carouselService.delete(id);
        return new ResultBean();
    }
}
