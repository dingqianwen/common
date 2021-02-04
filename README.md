# 📌 一些常用工具 📌

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![GitHub Stars](https://img.shields.io/github/stars/dingqianwen/common)](https://github.com/dingqianwen/common/stargazers)
[![GitHub Forks](https://img.shields.io/github/forks/dingqianwen/common)](https://github.com/dingqianwen/common/fork)
[![GitHub issues](https://img.shields.io/github/issues/dingqianwen/common.svg)](https://github.com/dingqianwen/common/issues)
[![Percentage of issues still open](http://isitmaintained.com/badge/open/dingqianwen/common.svg)](https://github.com/dingqianwen/common/issues "Percentage of issues still open")

#### 提供很多比较实用的工具，部分工具使用参考以下文档，更多使用方式敬请期待！

首先引入以下依赖：

```xml
<!-- https://mvnrepository.com/artifact/cn.ruleengine/common -->
<dependency>
    <groupId>cn.ruleengine</groupId>
    <artifactId>common</artifactId>
    <version>1.1</version>
</dependency>
```

## 多线程

### 多线程同步执行：

```java
public class Test {
    
    @Test
    public void mergeTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        long startTime = System.currentTimeMillis();
        List<String> merge = AsyncUtils.merge(executorService, true, new Concurrent<String>() {
            @Override
            public String async() throws Exception {
                // 操作数据 1
                Thread.sleep(1000);
                return "1";
            }
        }, new Concurrent<String>() {
            @Override
            public String async() throws Exception {
                // 操作数据 2
                Thread.sleep(1000);
                return "2";
            }
        });
        // print ["1","2"]
        System.out.println(merge);
        // print 1058
        System.out.println(System.currentTimeMillis() - startTime);
        executorService.shutdown();
    }
    
}
```

### 多线程批量执行：

```java
public class Test {
    
    @Test
    public void batchTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
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
        // print ["11",21""]
        System.out.println(batch);
        // print 1034
        System.out.println(System.currentTimeMillis() - startTime);
        executorService.shutdown();
    }
    
}
```

## 集合

### 集合按Key去重

```java
public class Test {
    
    @Test
    public void distinctByKeyTest() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User(1, "1"));
        users.add(new User(1, "2"));
        List<User> collect = users.stream().filter(CollUtils.distinctByKey(User::getId)).collect(Collectors.toList());
        // print [User(id=1, name=1)]
        System.out.println(collect);
    }

    @Data
    @AllArgsConstructor
    private static class User {
        private Integer id;
        private String name;
    }
    
}
```
