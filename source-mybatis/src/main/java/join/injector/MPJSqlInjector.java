package join.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import join.method.*;

import java.util.List;

/**
 * SQL 注入器
 *
 * @author yulichang
 * @see DefaultSqlInjector
 */
public class MPJSqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> list = super.getMethodList(mapperClass);
        list.add(new SelectJoinOne());
        list.add(new SelectJoinList());
        list.add(new SelectJoinPage());
        list.add(new SelectJoinMap());
        list.add(new SelectJoinMaps());
        list.add(new SelectJoinMapsPage());
        list.add(new InsertBatchSomeColumn());
        return list;
    }

}
