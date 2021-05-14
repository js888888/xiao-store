package join.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import join.interfaces.MPJBaseJoin;
import join.toolkit.Constant;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author yulichang
 * @see BaseMapper
 */
public interface MPJBaseMapper<T> extends BaseMapper<T> {

    /**
     * 连表查询返回一条记录
     *
     * @param myWrapper joinWrapper
     * @param clazz     resultType
     */
    <DTO> DTO selectJoinOne(@Param(Constant.CLAZZ) Class<DTO> clazz,
                            @Param(Constants.WRAPPER) MPJBaseJoin myWrapper);

    /**
     * 连表查询返回Map
     *
     * @param myWrapper joinWrapper
     */
    Map<String, Object> selectJoinMap(@Param(Constants.WRAPPER) MPJBaseJoin myWrapper);

    /**
     * 连表查询返回记录集合
     *
     * @param myWrapper joinWrapper
     * @param clazz     resultType
     */
    <DTO> List<DTO> selectJoinList(@Param(Constant.CLAZZ) Class<DTO> clazz,
                                   @Param(Constants.WRAPPER) MPJBaseJoin myWrapper);

    /**
     * 连表查询返回Map集合
     *
     * @param myWrapper joinWrapper
     */
    List<Map<String, Object>> selectJoinMaps(@Param(Constants.WRAPPER) MPJBaseJoin myWrapper);

    /**
     * 连表查询返回记录集合并分页
     *
     * @param myWrapper joinWrapper
     * @param clazz     resultType
     * @param <DTO>     分页返回对象
     */
    <DTO, P extends IPage<?>> IPage<DTO> selectJoinPage(P page,
                                                        @Param(Constant.CLAZZ) Class<DTO> clazz,
                                                        @Param(Constants.WRAPPER) MPJBaseJoin myWrapper);

    /**
     * 连表查询返回Map集合并分页
     *
     * @param myWrapper joinWrapper
     */
    <P extends IPage<?>> IPage<Map<String, Object>> selectJoinMapsPage(P page,
                                                                       @Param(Constants.WRAPPER) MPJBaseJoin myWrapper);
    /**
     * 批量插入
     *
     * @param entityList 带插人的对象集合
     * @auther jing 
     * @date 2021/3/25 0025
     **/
    int insertBatchSomeColumn(Collection<T> entityList);
}
