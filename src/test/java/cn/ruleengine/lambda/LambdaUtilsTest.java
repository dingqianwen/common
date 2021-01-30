package cn.ruleengine.lambda;

import lombok.Getter;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author dingqianwen
 * @date 2021/1/29
 * @since 1.0.0
 */
public class LambdaUtilsTest {

    @Getter
    private String name;

    public static void main(String[] args) {
        System.out.println(LambdaUtils.get(LambdaUtilsTest::getName));
        System.out.println(LambdaUtils.get(LambdaUtilsTest::getName));

    }

}
