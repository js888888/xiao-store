package join.query;

import com.baomidou.mybatisplus.core.conditions.AbstractLambdaWrapper;
import com.baomidou.mybatisplus.core.conditions.SharedString;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import join.query.interfaces.MPJJoin;
import join.toolkit.Constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * copy {@link com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper}
 * <p>
 * sqlSelect 由覆盖改为追加
 *
 * @author yulichang
 */
@SuppressWarnings("all")
public class MPJLambdaQueryWrapper<T> extends AbstractLambdaWrapper<T, MPJLambdaQueryWrapper<T>>
        implements Query<MPJLambdaQueryWrapper<T>, T, SFunction<T, ?>>, MPJJoin<MPJLambdaQueryWrapper<T>> {

    /**
     * 查询字段
     */
    private SharedString sqlSelect = new SharedString();

    /**
     * 连表字段
     */
    private SharedString from = SharedString.emptyString();

    /**
     * 主表别名
     */
    private final SharedString alias = new SharedString(Constant.TABLE_ALIAS);


    /**
     * 不建议直接 new 该实例，使用 Wrappers.lambdaQuery(entity)
     */
    public MPJLambdaQueryWrapper() {
        this((T) null);
    }

    /**
     * 不建议直接 new 该实例，使用 Wrappers.lambdaQuery(entity)
     */
    public MPJLambdaQueryWrapper(T entity) {
        super.setEntity(entity);
        super.initNeed();
    }

    /**
     * 不建议直接 new 该实例，使用 Wrappers.lambdaQuery(entity)
     */
    public MPJLambdaQueryWrapper(Class<T> entityClass) {
        super.setEntityClass(entityClass);
        super.initNeed();
    }

    /**
     * 不建议直接 new 该实例，使用 Wrappers.lambdaQuery(...)
     */
    MPJLambdaQueryWrapper(T entity, Class<T> entityClass, SharedString from, SharedString sqlSelect, AtomicInteger paramNameSeq,
                          Map<String, Object> paramNameValuePairs, MergeSegments mergeSegments,
                          SharedString lastSql, SharedString sqlComment, SharedString sqlFirst) {
        super.setEntity(entity);
        super.setEntityClass(entityClass);
        this.paramNameSeq = paramNameSeq;
        this.paramNameValuePairs = paramNameValuePairs;
        this.expression = mergeSegments;
        this.sqlSelect = sqlSelect;
        this.from = from;
        this.lastSql = lastSql;
        this.sqlComment = sqlComment;
        this.sqlFirst = sqlFirst;
    }

    /**
     * SELECT 部分 SQL 设置
     *
     * @param columns 查询字段
     */
    @SafeVarargs
    @Override
    public final MPJLambdaQueryWrapper<T> select(SFunction<T, ?>... columns) {
        return select(true, columns);
    }

    @SafeVarargs
    public final MPJLambdaQueryWrapper<T> select(boolean condition, SFunction<T, ?>... columns) {
        if (condition && ArrayUtils.isNotEmpty(columns)) {
            String s = columnsToString(false, columns);
            if (StringUtils.isBlank(sqlSelect.getStringValue())) {
                this.sqlSelect.setStringValue(s);
            } else {
                this.sqlSelect.setStringValue(this.getSqlSelect() + StringPool.COMMA + s);
            }
        }
        return typedThis;
    }

    @Override
    protected String columnToString(SFunction<T, ?> column, boolean onlyColumn) {
        return Constant.TABLE_ALIAS + StringPool.DOT + super.columnToString(column, onlyColumn);
    }

    @SafeVarargs
    public final MPJLambdaQueryWrapper<T> select(String... columns) {
        return select(true, columns);
    }

    @SafeVarargs
    public final MPJLambdaQueryWrapper<T> select(boolean condition, String... columns) {
        if (condition && ArrayUtils.isNotEmpty(columns)) {
            String s = String.join(StringPool.COMMA, columns);
            if (StringUtils.isBlank(sqlSelect.getStringValue())) {
                this.sqlSelect.setStringValue(s);
            } else {
                this.sqlSelect.setStringValue(this.getSqlSelect() + StringPool.COMMA + s);
            }
        }
        return typedThis;
    }

    /**
     * 过滤查询的字段信息(主键除外!)
     * <p>例1: 只要 java 字段名以 "test" 开头的             -> select(i -&gt; i.getProperty().startsWith("test"))</p>
     * <p>例2: 只要 java 字段属性是 CharSequence 类型的     -> select(TableFieldInfo::isCharSequence)</p>
     * <p>例3: 只要 java 字段没有填充策略的                 -> select(i -&gt; i.getFieldFill() == FieldFill.DEFAULT)</p>
     * <p>例4: 要全部字段                                   -> select(i -&gt; true)</p>
     * <p>例5: 只要主键字段                                 -> select(i -&gt; false)</p>
     *
     * @param predicate 过滤方式
     * @return this
     */
    @Override
    public MPJLambdaQueryWrapper<T> select(Class<T> entityClass, Predicate<TableFieldInfo> predicate) {
        return select(true, entityClass, predicate);
    }

    public MPJLambdaQueryWrapper<T> select(boolean condition, Class<T> entityClass, Predicate<TableFieldInfo> predicate) {
        if (condition) {
            if (entityClass == null) {
                entityClass = getEntityClass();
            } else {
                setEntityClass(entityClass);
            }
            Assert.notNull(entityClass, "entityClass can not be null");
            String s = TableInfoHelper.getTableInfo(entityClass).chooseSelect(predicate);
            List<String> list = Arrays.stream(s.split(StringPool.COMMA)).map(i -> Constant.TABLE_ALIAS + StringPool.DOT + i).collect(Collectors.toList());
            String join = String.join(StringPool.COMMA, list);
            if (StringUtils.isBlank(sqlSelect.getStringValue())) {
                this.sqlSelect.setStringValue(join);
            } else {
                this.sqlSelect.setStringValue(this.getSqlSelect() + StringPool.COMMA + join);
            }
        }
        return typedThis;
    }

    public final MPJLambdaQueryWrapper<T> selectAll(Class<T> clazz) {
        return selectAll(true, clazz);
    }

    public final MPJLambdaQueryWrapper<T> selectAll(boolean condition, Class<T> clazz) {
        if (condition) {
            TableInfo info = TableInfoHelper.getTableInfo(clazz);
            List<String> list = new ArrayList<>();
            if (info.havePK()) {
                list.add(Constant.TABLE_ALIAS + StringPool.DOT + info.getKeyColumn());
            }
            list.addAll(info.getFieldList().stream().map(i -> Constant.TABLE_ALIAS + StringPool.DOT + i.getColumn()).collect(Collectors.toList()));
            String join = String.join(StringPool.COMMA, list);
            if (StringUtils.isBlank(sqlSelect.getStringValue())) {
                this.sqlSelect.setStringValue(join);
            } else {
                this.sqlSelect.setStringValue(this.getSqlSelect() + StringPool.COMMA + join);
            }
        }
        return typedThis;
    }

    /**
     * 返回一个支持 lambda 函数写法的 wrapper
     */
    public MPJQueryWrapper<T> stringQuery() {
        return new MPJQueryWrapper<>(getEntity(), getEntityClass(), paramNameSeq, paramNameValuePairs,
                expression, sqlSelect, from, lastSql, sqlComment, sqlFirst);
    }

    @Override
    public String getSqlSelect() {
        return sqlSelect.getStringValue();
    }

    public String getFrom() {
        return from.getStringValue();
    }


    public String getAlias() {
        return alias.getStringValue();
    }

    /**
     * 用于生成嵌套 sql
     * <p>故 sqlSelect from不向下传递</p>
     */
    @Override
    protected MPJLambdaQueryWrapper<T> instance() {
        return new MPJLambdaQueryWrapper<>(getEntity(), getEntityClass(), null, null, paramNameSeq, paramNameValuePairs,
                new MergeSegments(), SharedString.emptyString(), SharedString.emptyString(), SharedString.emptyString());
    }

    @Override
    public void clear() {
        super.clear();
        sqlSelect.toNull();
    }

    @Override
    public MPJLambdaQueryWrapper<T> join(String keyWord, boolean condition, String joinSql) {
        if (condition) {
            from.setStringValue(from.getStringValue() + keyWord + joinSql);
        }
        return typedThis;
    }
}
