package com.zpkj.exam.service;

import java.util.List;
import com.github.pagehelper.Page;
import com.zpkj.exam.domain.TrafficArea;
import com.zpkj.exam.domain.form.TrafficAreaForm;
import com.zpkj.exam.domain.query.TrafficAreaQuery;

/**
 * TrafficAreaService 
 */
public interface TrafficAreaService {

    /**
     * 查询 TrafficArea
     */
    List<TrafficArea> find(final TrafficAreaQuery query);
    
    /**
     * 分页查询 TrafficArea
     */
    Page<TrafficArea> findByPage(final TrafficAreaQuery query);

    /**
     * 通过id得到一个 TrafficArea
     */
    TrafficArea get(final String id);
    
    /**
     * 新增 TrafficArea
     */
    void add(final TrafficAreaForm form);

    /**
     * 修改 TrafficArea
     */
    int update(final TrafficAreaForm form);
    
    /**
     * 删除一个 TrafficArea
     */
    int delete(final String id);
	
	/**
     * 修改状态
     */
    int changeStatus(final String id, final Integer status);

}