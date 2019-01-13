package com.zpkj.exam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zpkj.exam.domain.AttachmentInfo;
import com.zpkj.exam.domain.form.AttachmentInfoForm;
import com.zpkj.exam.domain.query.AttachmentInfoQuery;
import com.zpkj.exam.dao.AttachmentInfoDao;
import com.zpkj.exam.service.AttachmentInfoService;


/**
 * AttachmentInfoService 
 */
@Service
public class AttachmentInfoServiceImpl implements AttachmentInfoService {
    @Autowired 
    private AttachmentInfoDao attachmentInfoDao;
	
	/**
     * 查询 AttachmentInfo
     */
    public List<AttachmentInfo> find(final AttachmentInfoQuery query) {
        return attachmentInfoDao.find(query);
    }
    
    /**
     * 分页查询 AttachmentInfo
     */
    public Page<AttachmentInfo> findByPage(final AttachmentInfoQuery query) {
        PageHelper.startPage(query.getPage().intValue(), query.getLimit().intValue());
        return (Page<AttachmentInfo>) attachmentInfoDao.find(query);
    }

    /**
     * 通过id得到一个 AttachmentInfo
     */
    public AttachmentInfo get(final String id) {
        return attachmentInfoDao.get(id);
    }
    
    /**
     * 新增 AttachmentInfo
     */
    public void add(final AttachmentInfoForm form) {
        attachmentInfoDao.add(form);
    }

    /**
     * 修改 AttachmentInfo
     */
    public int update(final AttachmentInfoForm form) {
        return attachmentInfoDao.update(form);
    }
    
    /**
     * 删除一个 AttachmentInfo
     */
    public int delete(final String id) {
        return attachmentInfoDao.delete(id);
    }
	
	/**
     * 修改状态
     */
    public int changeStatus(final String id, final Integer status) {
        return attachmentInfoDao.changeStatus(id, status);
    }

    @Override
    public AttachmentInfoForm findAttachmentByid(String annexUrl) {
        return attachmentInfoDao.findAttachmentByid(annexUrl);
    }

}