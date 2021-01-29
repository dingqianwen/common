package cn.ruleengine.thread;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author dingqianwen
 * @date 2021/1/29
 * @since 1.0.0
 */
public class AsyncUtilsTest {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        long startTime = System.currentTimeMillis();
        List<String> merge = AsyncUtils.merge(executorService, true, new Concurrent<String>() {
            @Override
            public String async() throws Exception {
                Thread.sleep(1000);
                return "1";
            }
        }, new Concurrent<String>() {
            @Override
            public String async() throws Exception {
                Thread.sleep(1000);
                return "2";
            }
        }, new Concurrent<String>() {
            @Override
            public String async() throws Exception {
                Thread.sleep(1000);
                return "3";
            }
        }, new Concurrent<String>() {
            @Override
            public String async() throws Exception {
                Thread.sleep(1000);
                return "4";
            }
        });
        System.out.println(merge);
        // 1058
        System.out.println(System.currentTimeMillis() - startTime);
        executorService.shutdown();
    }
}
