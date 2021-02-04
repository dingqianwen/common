package cn.ruleengine.common.thread;


import org.junit.After;
import org.junit.Test;

import java.util.Arrays;
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
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Test
    public void mergeTest() {
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
    }

    @Test
    public void batchTest() {
        long startTime = System.currentTimeMillis();
        List<String> datas = Arrays.asList("1", "2");
        List<String> batch = AsyncUtils.batch(executorService, datas, 2, new BatchExecutor<String, String>() {
            @Override
            public String async(String data) throws Exception {
                Thread.sleep(1000);
                return data + "1";
            }

            @Override
            public void onError(String data, Exception e) throws Exception {

            }
        });
        System.out.println(batch);
        // 1034
        System.out.println(System.currentTimeMillis() - startTime);
    }

    @After
    public void after() {
        executorService.shutdown();
    }
}
