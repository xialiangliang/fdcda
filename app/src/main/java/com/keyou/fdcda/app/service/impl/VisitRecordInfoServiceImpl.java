package com.keyou.fdcda.app.service.impl;

import java.util.ArrayList;
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
		List<VisitRecordInfo> list = new ArrayList<>();
        Long count = visitRecordInfoMapper.findPageCount(query.getQueryData());
        if (count > 0) {
            query.addQueryData("startRecord", (query.getPageIndex() - 1) * query.getRowsPerPage());
            query.addQueryData("endRecord", query.getRowsPerPage());
            list = visitRecordInfoMapper.findPage(query.getQueryData());
        }
        return new PageResult<>(list, count.intValue(), query);
	}

	@Override
	public List<VisitRecordInfo> selectDayCountReport(Map<String, Object> map) {
		return visitRecordInfoMapper.selectDayCountReport(map);
	}

	@Override
	public List<VisitRecordInfo> selectDayDetailReport(Map<String, Object> map) {
		return   visitRecordInfoMapper.selectDayDetailReport(map);
	}
	

	
}
