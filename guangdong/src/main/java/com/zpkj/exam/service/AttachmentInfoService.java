package com.zpkj.exam.service;

import java.util.List;
import com.github.pagehelper.Page;
import com.zpkj.exam.domain.AttachmentInfo;
import com.zpkj.exam.domain.form.AttachmentInfoForm;
import com.zpkj.exam.domain.query.AttachmentInfoQuery;

/**
 * AttachmentInfoService 
 */
public interface AttachmentInfoService {

    /**
     * 查询 AttachmentInfo
     */
    List<AttachmentInfo> find(final AttachmentInfoQuery query);
    
    /**
     * 分页查询 AttachmentInfo
     */
    Page<AttachmentInfo> findByPage(final AttachmentInfoQuery query);

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
    int changeStatus(final String id, final Integer status);

    AttachmentInfoForm findAttachmentByid(String annexUrl);
}