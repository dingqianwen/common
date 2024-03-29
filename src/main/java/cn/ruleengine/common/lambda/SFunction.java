package cn.ruleengine.common.lambda;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author dingqianwen
 * @date 2021/1/29
 * @since 1.0.0
 */
@FunctionalInterface
public interface SFunction<T, R> extends Serializable {

    /**
     * apply
     *
     * @param t t
     * @return R
     */
    R apply(T t);

}
