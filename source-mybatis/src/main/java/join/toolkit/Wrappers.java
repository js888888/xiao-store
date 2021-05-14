package join.toolkit;

import join.common.JoinLambdaWrapper;
import join.common.support.alias.AliasLambdaQueryWrapper;
import join.common.support.alias.AliasQueryWrapper;
import join.query.MPJLambdaQueryWrapper;
import join.query.MPJQueryWrapper;
import join.wrapper.MPJJoinLambdaQueryWrapper;

/**
 * Wrapper 条件构造
 *
 * @author yulichang
 */
public class Wrappers {

    public static <T> MPJQueryWrapper<T> queryJoin() {
        return new MPJQueryWrapper<>();
    }

    public static <T> MPJLambdaQueryWrapper<T> lambdaJoin() {
        return new MPJLambdaQueryWrapper<>();
    }

    public static <T> MPJJoinLambdaQueryWrapper<T> lambdaJoinWrapper() {
        return new MPJJoinLambdaQueryWrapper<>();
    }

    public static <T> JoinLambdaWrapper<T> commonJoin() {
        return new JoinLambdaWrapper<>();
    }

    public static <T> AliasQueryWrapper<T> aliasQueryJoin() {
        return new AliasQueryWrapper<>();
    }

    public static <T> AliasLambdaQueryWrapper<T> aliasLambdaJoin() {
        return new AliasLambdaQueryWrapper<>();
    }
}
