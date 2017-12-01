 package com.keyou.fdcda.app.service.impl;

import com.keyou.fdcda.api.model.BlacklistDetails;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.BlacklistDetailsService;
import com.keyou.fdcda.app.dao.BlacklistDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("blacklistDetailsService")
public class BlacklistDetailsServiceImpl implements BlacklistDetailsService {
    private final static Logger logger = LoggerFactory.getLogger(BlacklistDetailsServiceImpl.class);

    @Autowired
    private BlacklistDetailsMapper blacklistDetailsMapper;

    @Override
    public BlacklistDetails findById(Long id) {
        return blacklistDetailsMapper.findById(id);
    }

    @Override
    public Long update(BlacklistDetails entity) {
        return blacklistDetailsMapper.update(entity);
    }

    @Override
    public void deleteById(Long id) {
        blacklistDetailsMapper.deleteById(id);
    }

    @Override
    public Integer save(BlacklistDetails entity) {
        return blacklistDetailsMapper.save(entity);
    }

    @Override
    public Long findPageCount(Map<String, Object> map) {
        return blacklistDetailsMapper.findPageCount(map);
    }

    @Override
    public PageResult<BlacklistDetails> findPage(PaginationQuery query) {
        List<BlacklistDetails> list = new ArrayList<>();
        Long count = blacklistDetailsMapper.findPageCount(query.getQueryData());
        if (count > 0) {
            query.addQueryData("startRecord", (query.getPageIndex() - 1) * query.getRowsPerPage());
            query.addQueryData("endRecord", query.getRowsPerPage());
            list = blacklistDetailsMapper.findPage(query.getQueryData());
        }
        return new PageResult<>(list, count.intValue(), query);
    }

    @Override
    public List<BlacklistDetails> findAllPage(Map<String, Object> map) {
        return blacklistDetailsMapper.findAllPage(map);
    }
}
