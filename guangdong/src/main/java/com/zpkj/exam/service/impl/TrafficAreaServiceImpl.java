package com.zpkj.exam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zpkj.exam.domain.TrafficArea;
import com.zpkj.exam.domain.form.TrafficAreaForm;
import com.zpkj.exam.domain.query.TrafficAreaQuery;
import com.zpkj.exam.dao.TrafficAreaDao;
import com.zpkj.exam.service.TrafficAreaService;


/**
 * TrafficAreaService 
 */
@Service
public class TrafficAreaServiceImpl implements TrafficAreaService {
    @Autowired 
    private TrafficAreaDao trafficAreaDao;

	/**
     * 查询 TrafficArea
     */
    public List<TrafficArea> find(final TrafficAreaQuery query) {
        return trafficAreaDao.find(query);
    }
    
    /**
     * 分页查询 TrafficArea
     */
    public Page<TrafficArea> findByPage(final TrafficAreaQuery query) {
        PageHelper.startPage(query.getPage().intValue(), query.getLimit().intValue());
        return (Page<TrafficArea>) trafficAreaDao.find(query);
    }

    /**
     * 通过id得到一个 TrafficArea
     */
    public TrafficArea get(final String id) {
        return trafficAreaDao.get(id);
    }
    
    /**
     * 新增 TrafficArea
     */
    public void add(final TrafficAreaForm form) {
        trafficAreaDao.add(form);
    }

    /**
     * 修改 TrafficArea
     */
    public int update(final TrafficAreaForm form) {
        return trafficAreaDao.update(form);
    }
    
    /**
     * 删除一个 TrafficArea
     */
    public int delete(final String id) {
        return trafficAreaDao.delete(id);
    }
	
	/**
     * 修改状态
     */
    public int changeStatus(final String id, final Integer status) {
        return trafficAreaDao.changeStatus(id, status);
    }
	
}