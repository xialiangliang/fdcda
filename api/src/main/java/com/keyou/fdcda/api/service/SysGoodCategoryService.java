package com.keyou.fdcda.api.service;

import com.keyou.fdcda.api.model.SysGoodCategory;
import com.keyou.fdcda.api.service.base.BaseService;
import com.keyou.fdcda.api.utils.Result;

import java.util.List;
import java.util.Map;


public interface SysGoodCategoryService extends BaseService<SysGoodCategory, Long> {

    Result<List<SysGoodCategory>> getTopologicalCategory(Map<String, Object> map);
    
    Result<List<SysGoodCategory>> findListForSelect(Map<String, Object> map);
}
