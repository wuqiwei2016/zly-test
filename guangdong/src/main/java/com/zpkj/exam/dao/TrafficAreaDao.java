package com.zpkj.exam.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.zpkj.exam.domain.TrafficArea;
import com.zpkj.exam.domain.form.TrafficAreaForm;
import com.zpkj.exam.domain.query.TrafficAreaQuery;

/**
 * TrafficAreaDao 
 */
public interface TrafficAreaDao {
    
    /**
     * 查询 TrafficArea
     */
    List<TrafficArea> find(final TrafficAreaQuery query);

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
    int changeStatus(@Param("id") final String id, @Param("status") final Integer status);

}