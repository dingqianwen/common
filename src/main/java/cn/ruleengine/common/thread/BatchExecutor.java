package cn.ruleengine.common.thread;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author dingqianwen
 * @date 2021/1/29
 * @since 1.0.0
 */
public interface BatchExecutor<IN, OUT> {

    /**
     * 异步处理数据
     *
     * @param in 输入数据
     * @return 输出数据
     * @throws Exception e
     */
    OUT async(IN in) throws Exception;

    /**
     * 发生异常时
     *
     * @param in 输入数据
     * @param e  异常信息
     * @throws Exception 如果抛出任意异常，则不跳过，结束此次所有线程运行，并返回错误信息
     */
    void onError(IN in, Exception e) throws Exception;

}
