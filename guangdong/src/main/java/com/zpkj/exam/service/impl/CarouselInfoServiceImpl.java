package com.zpkj.exam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zpkj.exam.domain.CarouselInfo;
import com.zpkj.exam.domain.form.CarouselInfoForm;
import com.zpkj.exam.domain.query.CarouselInfoQuery;
import com.zpkj.exam.dao.CarouselInfoDao;
import com.zpkj.exam.service.CarouselInfoService;


/**
 * CarouselInfoService 
 */
@Service
public class CarouselInfoServiceImpl implements CarouselInfoService {
    @Autowired 
    private CarouselInfoDao carouselInfoDao;
	
	/**
     * 查询 CarouselInfo
     */
    public List<CarouselInfo> find(final CarouselInfoQuery query) {
        return carouselInfoDao.find(query);
    }
    
    /**
     * 分页查询 CarouselInfo
     */
    public Page<CarouselInfo> findByPage(final CarouselInfoQuery query) {
        PageHelper.startPage(query.getPage().intValue(), query.getLimit().intValue());
        return (Page<CarouselInfo>) carouselInfoDao.find(query);
    }

    /**
     * 通过id得到一个 CarouselInfo
     */
    public CarouselInfo get(final String id) {
        return carouselInfoDao.get(id);
    }
    
    /**
     * 新增 CarouselInfo
     */
    public void add(final CarouselInfoForm form) {
        carouselInfoDao.add(form);
    }

    /**
     * 修改 CarouselInfo
     */
    public int update(final CarouselInfoForm form) {
        return carouselInfoDao.update(form);
    }
    
    /**
     * 删除一个 CarouselInfo
     */
    public int delete(final String id) {
        return carouselInfoDao.delete(id);
    }
	
	/**
     * 修改状态
     */
    public int changeStatus(final String id, final Integer status) {
        return carouselInfoDao.changeStatus(id, status);
    }
	
}