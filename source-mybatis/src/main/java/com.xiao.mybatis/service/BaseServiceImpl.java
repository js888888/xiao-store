package com.ky.mybatis.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ky.mybatis.mapper.EasyBaseMapper;
import join.base.MPJBaseMapper;
import join.base.MPJBaseServiceImpl;

import java.util.Collection;

public class BaseServiceImpl<M extends MPJBaseMapper<T>, T> extends MPJBaseServiceImpl<M, T> implements IBaseService<T> {

    @Override
    public int insertBatchSomeColumn(final Collection<T> entityList) {
        return baseMapper.insertBatchSomeColumn(entityList);
    }
}
