package com.ky.mybatis.plus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ky.common.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill.....");
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now())
                .strictInsertFill(metaObject,"isStop", Boolean.class,false); // 起始版本 3.3.0(推荐使用)
        log.info("end insert fill....." + LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐)
        log.info("end update fill....." + LocalDateTime.now());
    }
}
