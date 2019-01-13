package com.zpkj.exam.controller.common;

import com.zpkj.exam.common.ResultBean;
import com.zpkj.exam.domain.query.TrafficAreaQuery;
import com.zpkj.exam.service.TrafficAreaService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by wanshipeng on 2017/3/17.
 */
@RestController
@RequestMapping("/area")
@CrossOrigin
public class AreaController {
    @Autowired
    private TrafficAreaService trafficAreaService;

    @ApiOperation(value = "获取地区列表", notes = "")
    @ApiImplicitParam(name = "areaParentno", value = "父地区编码", paramType = "query", dataType = "String")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResultBean find(@ApiIgnore TrafficAreaQuery query) {
        return new ResultBean(trafficAreaService.find(query));
    }
}
