package cn.ruleengine.common.collection;

import cn.ruleengine.common.collection.CollUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author dingqianwen
 * @date 2021/1/29
 * @since 1.0.0
 */
public class CollUtilsTest {

    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User(1, "1"));
        users.add(new User(1, "2"));
        List<User> collect = users.stream().filter(CollUtils.distinctByKey(User::getName)).collect(Collectors.toList());
        System.out.println(collect);
    }

    @Data
    @AllArgsConstructor
    private static class User {
        private Integer id;
        private String name;
    }

}
