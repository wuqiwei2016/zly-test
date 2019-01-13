package com.zpkj.exam.controller.common;

import com.zpkj.exam.common.ResultBean;
import com.zpkj.exam.domain.query.CarouselInfoQuery;
import com.zpkj.exam.service.CarouselInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wanshipeng on 2018/6/12.
 */
@RestController
@RequestMapping("/carousel")
@CrossOrigin
public class ForeCarouselController {

    @Autowired
    CarouselInfoService carouselService;

    @ApiOperation(value = "获取轮播图列表", notes = "")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResultBean getCarouselInfoList(CarouselInfoQuery query) {
        query.setStatus(1);
        return new ResultBean(carouselService.find(query));
    }
}
