package cn.ruleengine.common.lambda;


import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
        String key = getSerializedLambda(func).getImplMethodName();
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
     * getSerializedLambda and cache
     *
     * @param func 函数接口
     * @param <T>  t
     * @return SerializedLambda
     */
    public static <T, R> SerializedLambda getSerializedLambda(SFunction<T, R> func) {
        try {
            Method method = func.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(true);
            return (SerializedLambda) method.invoke(func);
        } catch (NoSuchMethodException e) {
            // mybatis plus copy
            String message = "Cannot find method writeReplace, please make sure that the lambda composite class is currently passed in";
            throw new RuntimeException(message);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


}
