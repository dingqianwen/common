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
    <version>1.4</version>
</dependency>
```

## 多线程

### 多线程数据同步执行：

#### 功能简介

多线程同时执行一批数据，并获取返回结果。

#### 操作案例

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

### 多线程数据批量执行：

#### 功能简介

批量处理一批数据时使用，例如解析的Excel中数据进行多线程批量校验与导入等。

#### 操作案例

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

#### 功能简介

集合中，根据对象中指定的属性名进行去除重复。

#### 操作案例

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

## Lambda

### LambdaUtils

#### 功能简介

主要使用方法引用时,获取方法引用的名称

#### 操作案例

我们编写一个Executor类,想获取这个类中的hello方法名称,具体操作如下

```java

@Data
public class Executor {
    private String name;

    public String hello() {
        return null;
    }

    public static void main(String[] args) {
        System.out.println(LambdaUtils.get(Executor::getName, true));
        System.out.println(LambdaUtils.get(Executor::hello, false));
    }
}
```

输出结果:

```
name
hello
```

注意,使用上例方法引用时注意被引用的方法需要有返回值

#### 使用案例

例如我们在操作mongodb时,使用spring的MongoTemplate,可能会存在不足之处,例如:

```java
Query query=new Query(Criteria.where("message").is("查询关键字")
        .and("type").is("查询NullPointerException"));
        ExceptionMessageBean one=mongoTemplate.findOne(query,ExceptionMessageBean.class);
        log.info("find:{}",one);
```

我们在构建条件时,where("message")不应该把此message以字符串的形式出现在这里,后期如果更改字段名称时,不能及时看到此处出现的问题  
例如下面我对Criteria经行了优化,可能会使代码更好些!

```java
Query query=new Query(CriteriaPlus.where(ExceptionMessageBean::getMessage).is("查询关键字")
        .and(ExceptionMessageBean::getType).is("查询NullPointerException"));
        ExceptionMessageBean one=mongoTemplate.findOne(query,ExceptionMessageBean.class);
        log.info("find:{}",one);
```

当前还有其他功能,需要自己去挖掘

