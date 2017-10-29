package com.keyou.fdcda.app.service.impl;

import com.keyou.fdcda.api.model.ImageBaseSend;
import com.keyou.fdcda.api.model.base.PageResult;
import com.keyou.fdcda.api.model.base.PaginationQuery;
import com.keyou.fdcda.api.service.ImageBaseSendService;
import com.keyou.fdcda.app.dao.ImageBaseSendMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("imageBaseSendService")
public class ImageBaseSendServiceImpl implements ImageBaseSendService {
    private final static Logger logger = LoggerFactory.getLogger(ImageBaseSendServiceImpl.class);

    @Autowired
    private ImageBaseSendMapper imageBaseSendMapper;

    @Override
    public ImageBaseSend findById(Long id) {
        return imageBaseSendMapper.findById(id);
    }

    @Override
    public Long update(ImageBaseSend entity) {
        return imageBaseSendMapper.update(entity);
    }

    @Override
    public void deleteById(Long id) {
        imageBaseSendMapper.deleteById(id);
    }

    @Override
    public Integer save(ImageBaseSend entity) {
        return imageBaseSendMapper.save(entity);
    }

    @Override
    public Long findPageCount(Map<String, Object> map) {
        return imageBaseSendMapper.findPageCount(map);
    }

    @Override
    public PageResult<ImageBaseSend> findPage(PaginationQuery query) {
        List<ImageBaseSend> list = new ArrayList<>();
        Long count = imageBaseSendMapper.findPageCount(query.getQueryData());
        if (count > 0) {
            query.addQueryData("startRecord", (query.getPageIndex() - 1) * query.getRowsPerPage());
            query.addQueryData("endRecord", query.getRowsPerPage());
            list = imageBaseSendMapper.findPage(query.getQueryData());
        }
        return new PageResult<>(list, count.intValue(), query);
    }

    @Override
    public List<ImageBaseSend> findAllPage(Map<String, Object> map) {
        return imageBaseSendMapper.findAllPage(map);
    }
}
