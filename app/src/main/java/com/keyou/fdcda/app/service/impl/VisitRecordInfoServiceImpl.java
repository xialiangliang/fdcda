package com.keyou.fdcda.app.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keyou.fdcda.api.model.VisitRecordInfo;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.VisitRecordInfoService;
import com.keyou.fdcda.app.dao.VisitRecordInfoMapper;

@Service("visitRecordInfoService")
@Transactional
public class VisitRecordInfoServiceImpl implements VisitRecordInfoService {
	
	@Autowired
	private VisitRecordInfoMapper visitRecordInfoMapper;	
	   
	@Transactional(readOnly=true)
    public VisitRecordInfo findById(java.lang.Long id)   {
        return visitRecordInfoMapper.findById(id);
    }
	
    @Override
	public void deleteById(Long id) {
    	visitRecordInfoMapper.deleteById(id);
	}

	@Override
	public Long findPageCount(Map<String, Object> map) {
		return visitRecordInfoMapper.findPageCount(map);
	}

	@Override
	public List<VisitRecordInfo> findAllPage(Map<String, Object> map) {
		return visitRecordInfoMapper.findAllPage(map);
	}

	public void delete(java.lang.Long id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        visitRecordInfoMapper.deleteById(id);
    }
	
	public Integer save(VisitRecordInfo visitRecordInfo)  {	    
		return visitRecordInfoMapper.save(visitRecordInfo);
	}
	
	public Long update(VisitRecordInfo visitRecordInfo)   {	
		 
	    return visitRecordInfoMapper.update(visitRecordInfo);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<VisitRecordInfo> findPage(PaginationQuery query)  {
		PageResult<VisitRecordInfo> result = null;
		try {
			Long count = visitRecordInfoMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<VisitRecordInfo> list = visitRecordInfoMapper.findPage(query.getQueryData());
				result = new PageResult<VisitRecordInfo>(list,count.intValue(),query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
