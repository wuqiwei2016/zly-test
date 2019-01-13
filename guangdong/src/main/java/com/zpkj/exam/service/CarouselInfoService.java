package com.zpkj.exam.service;

import java.util.List;
import com.github.pagehelper.Page;
import com.zpkj.exam.domain.CarouselInfo;
import com.zpkj.exam.domain.form.CarouselInfoForm;
import com.zpkj.exam.domain.query.CarouselInfoQuery;

/**
 * CarouselInfoService 
 */
public interface CarouselInfoService {

    /**
     * 查询 CarouselInfo
     */
    List<CarouselInfo> find(final CarouselInfoQuery query);
    
    /**
     * 分页查询 CarouselInfo
     */
    Page<CarouselInfo> findByPage(final CarouselInfoQuery query);

    /**
     * 通过id得到一个 CarouselInfo
     */
    CarouselInfo get(final String id);
    
    /**
     * 新增 CarouselInfo
     */
    void add(final CarouselInfoForm form);

    /**
     * 修改 CarouselInfo
     */
    int update(final CarouselInfoForm form);
    
    /**
     * 删除一个 CarouselInfo
     */
    int delete(final String id);
	
	/**
     * 修改状态
     */
    int changeStatus(final String id, final Integer status);

}