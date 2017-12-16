package com.keyou.fdcda.api.service;

import java.util.List;
import java.util.Map;

import com.keyou.fdcda.api.model.VisitRecordInfo;
import com.keyou.fdcda.api.service.base.BaseService;

public interface VisitRecordInfoService extends BaseService<VisitRecordInfo,Long>{
	/**
	 * 根据日期查询时间段内经营户的总体访问情况
	 * @param map
	 * @return
	 */
	List<VisitRecordInfo> selectDayCountReport(Map<String, Object> map);
	
	/**
	 * 根据日期查询时间段内，按天采购商访问情况。
	 * @param map
	 * @return
	 */
	
	List<VisitRecordInfo> selectDayDetailReport(Map<String, Object> map);
}
