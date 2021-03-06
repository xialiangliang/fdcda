package com.keyou.fdcda.api.service.base;

import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by zzq on 2017-07-08.
 */
public interface BaseService<T, K extends Serializable> {

    /**
     * 根据id获取对象
     * @param id 对象id
     * @return Object  返回对象  
     */
    T findById(K id);

    /**
     * 更新对象信息 
     * @param entity 实体类
     *
     */
    Long update(T entity);

    /**
     * 根据id删除对象
     * @param id 对象id
     *
     */
    void deleteById(K id);

    /**
     * 保存对象信息
     * @param entity 对象实体类
     *
     */
    Integer save(T entity);

    /**
     * 分页查询--总条数
     * @return Integer 总条数   
     */
    K findPageCount(Map<String, Object> map);

    /**
     * 分页查询列表
     * @return List<E> 返回数据集合   
     */
    PageResult<T> findPage(PaginationQuery query);

    /**
     * 查询列表
     * @return List<E> 返回数据集合   
     */
    List<T> findAllPage(Map<String, Object> map);
}
