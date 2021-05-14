package com.ky.mybatis.util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

public class MyBatisPlusUtil {

    private MyBatisPlusUtil() {
    }

    /**
     * 根据条件查询集合
     *
     * @param iService service实现
     * @param column 字段名
     * @param columnValue 字段值
     * @auther jing
     * @date 2020/12/29 0029
     **/
    public static <T> List<T> queryListByColumn(IService<T> iService, SFunction<T, ?> column, String columnValue) {
        return iService.list(queryByColumn(column, columnValue));
    }

    public static <T> List<T> queryListByColumn(IService<T> iService, SFunction<T, ?> column, Collection<?> columnValue) {
        return iService.list(queryByColumn(column, columnValue));
    }

//    public static <T> int insertList(IService<T> iService, SFunction<T, ?> column, String columnValue) {
//        iService.getBaseMapper().in
//    }
    // todo 等待完善

    /**
     * 根据条件删除
     * 直接执行
     * 返回 true 或 false
     *
     * @param iService service实现
     * @param column 字段名
     * @param columnValue 字段值
     * @auther jing
     * @date 2020/12/29 0029
     * */
    public static <T> boolean deleteQueryByColumn(IService<T> iService, SFunction<T, ?> column, String columnValue) {
        return iService.remove(queryByColumn(column, columnValue));
    }

    /**
     * 根据传入的参数组成query wrapper 对象
     *
     * @param column 字段名
     * @param columnValue 字段值
     * @auther jing 
     * @date 2020/12/29 0029
     **/
    public static <T> LambdaQueryWrapper<T> queryByColumn(SFunction<T, ?> column, String columnValue) {
        LambdaQueryWrapper<T> wrapper = getLambdaQueryWrapper();
        wrapper.eq(column, columnValue);
        return wrapper;
    }
    public static <T> LambdaQueryWrapper<T> queryByColumn(SFunction<T, ?> column, Collection<?> columnValue) {
        LambdaQueryWrapper<T> wrapper = getLambdaQueryWrapper();
        wrapper.in(column, columnValue);
        return wrapper;
    }

    private static <T> LambdaQueryWrapper<T> getLambdaQueryWrapper() {
        return new LambdaQueryWrapper<>();
    }

}
