package join.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import join.interfaces.MPJBaseJoin;

import java.util.List;
import java.util.Map;

/**
 * @author yulichang
 * @see ServiceImpl
 */
public class MPJBaseServiceImpl<M extends MPJBaseMapper<T>, T> extends ServiceImpl<M, T> implements MPJBaseService<T> {

    public <DTO> DTO selectJoinOne(Class<DTO> clazz, MPJBaseJoin wrapper) {
        return baseMapper.selectJoinOne(clazz, wrapper);
    }

    public <DTO> List<DTO> selectJoinList(Class<DTO> clazz, MPJBaseJoin wrapper) {
        return baseMapper.selectJoinList(clazz, wrapper);
    }

    public <DTO, P extends IPage<?>> IPage<DTO> selectJoinListPage(P page, Class<DTO> clazz, MPJBaseJoin wrapper) {
        return baseMapper.selectJoinPage(page, clazz, wrapper);
    }

    public Map<String, Object> selectJoinMap(MPJBaseJoin wrapper) {
        return baseMapper.selectJoinMap(wrapper);
    }

    public List<Map<String, Object>> selectJoinMaps(MPJBaseJoin wrapper) {
        return baseMapper.selectJoinMaps(wrapper);
    }

    public <P extends IPage<Map<String, Object>>> IPage<Map<String, Object>> selectJoinMapsPage(P page, MPJBaseJoin wrapper) {
        return baseMapper.selectJoinMapsPage(page, wrapper);
    }
}
