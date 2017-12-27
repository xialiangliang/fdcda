package com.keyou.fdcda.app.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.keyou.fdcda.api.model.VisitRecordInfo;
import com.keyou.fdcda.app.dao.base.BaseMapper;


@Repository
public interface VisitRecordInfoMapper extends BaseMapper<VisitRecordInfo, java.lang.Long> {	

	List<VisitRecordInfo> selectDayCountReport(Map<String, Object> map);
	
	List<VisitRecordInfo> selectDayDetailReport(Map<String, Object> map);
	
	
	List<VisitRecordInfo> selectDayCountMallReport(Map<String, Object> map);
}
