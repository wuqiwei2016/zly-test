package com.zpkj.exam.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.zpkj.exam.domain.AttachmentInfo;
import com.zpkj.exam.domain.form.AttachmentInfoForm;
import com.zpkj.exam.domain.query.AttachmentInfoQuery;

/**
 * AttachmentInfoDao 
 */
public interface AttachmentInfoDao {
    
    /**
     * 查询 AttachmentInfo
     */
    List<AttachmentInfo> find(final AttachmentInfoQuery query);

    /**
     * 通过id得到一个 AttachmentInfo
     */
    AttachmentInfo get(final String id);
    
    /**
     * 新增 AttachmentInfo
     */
    void add(final AttachmentInfoForm form);

    /**
     * 修改 AttachmentInfo
     */
    int update(final AttachmentInfoForm form);
    
    /**
     * 删除一个 AttachmentInfo
     */
    int delete(final String id);
    
    /**
     * 修改状态
     */
    int changeStatus(@Param("id") final String id, @Param("status") final Integer status);

    AttachmentInfoForm findAttachmentByid(String annexUrl);
}