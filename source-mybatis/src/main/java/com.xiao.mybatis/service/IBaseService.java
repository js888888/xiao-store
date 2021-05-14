package com.ky.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import join.base.MPJBaseService;

import java.util.Collection;

public interface IBaseService<T> extends MPJBaseService<T> {

    int insertBatchSomeColumn(Collection<T> entityList);
}
