package cn.ruleengine.common.thread;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author dingqianwen
 * @date 2021/1/29
 * @since 1.0.0
 */
@FunctionalInterface
public interface Concurrent<OUT> {

    /**
     * 异步处理数据
     *
     * @return 输出数据
     * @throws Exception e
     */
    OUT async() throws Exception;

}
