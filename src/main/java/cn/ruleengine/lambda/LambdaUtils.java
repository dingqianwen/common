package cn.ruleengine.lambda;


import org.springframework.util.SerializationUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.Objects;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author dingqianwen
 * @date 2021/1/29
 * @since 1.0.0
 */
public class LambdaUtils {
    /**
     * 根据方法引用,获取引用的方法名称
     *
     * @param func       函数接口
     * @param onlyColumn 为true时去get/is,首字母小写
     * @return column
     */
    public static <T, R> String get(SFunction<T, R> func, boolean onlyColumn) {
        String key = resolveProcess(func).getImplMethodName();
        if (!onlyColumn) {
            return key;
        }
        if (key.startsWith("get")) {
            key = key.substring(3);
        } else if (key.startsWith("is")) {
            key = key.substring(2);
        }
        return key.substring(0, 1).toLowerCase() + key.substring(1);
    }

    public static <T, R> String get(SFunction<T, R> func) {
        return get(func, true);
    }


    /**
     * @param func 函数接口
     * @return 返回解析后的 SerializedLambda
     */
    private static <T> SerializedLambda resolveProcess(SFunction<T, ?> func) {
        if (!func.getClass().isSynthetic()) {
            throw new RuntimeException("not lambda synthetic");
        }
        byte[] serialize = SerializationUtils.serialize(func);
        try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(Objects.requireNonNull(serialize))) {
            @Override
            protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
                Class<?> clazz = super.resolveClass(objectStreamClass);
                return clazz == java.lang.invoke.SerializedLambda.class ? SerializedLambda.class : clazz;
            }
        }) {
            return (SerializedLambda) objIn.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException("This is impossible to happen", e);
        }
    }
}
