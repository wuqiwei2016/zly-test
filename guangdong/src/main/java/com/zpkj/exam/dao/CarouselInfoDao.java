package com.zpkj.exam.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.zpkj.exam.domain.CarouselInfo;
import com.zpkj.exam.domain.form.CarouselInfoForm;
import com.zpkj.exam.domain.query.CarouselInfoQuery;

/**
 * CarouselInfoDao 
 */
public interface CarouselInfoDao {
    
    /**
     * 查询 CarouselInfo
     */
    List<CarouselInfo> find(final CarouselInfoQuery query);

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
    int changeStatus(@Param("id") final String id, @Param("status") final Integer status);

}