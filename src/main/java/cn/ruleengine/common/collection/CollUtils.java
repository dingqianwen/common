package cn.ruleengine.common.collection;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author dingqianwen
 * @date 2021/1/29
 * @since 1.0.0
 */
public class CollUtils {

    /**
     * 注意，此工具切分后，只是原始数据的引用，原对象修改，则切分后的数据也被修改
     *
     * @param list 被切分的集合
     * @param aFew 多少切一份
     * @param <T>  t
     * @return list
     */
    public static <T> List<List<T>> subList(List<T> list, int aFew) {
        if (aFew <= 0) {
            throw new IndexOutOfBoundsException();
        }
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        List<List<T>> arrayList = new ArrayList<>();
        int size = list.size();
        for (int i = 0; i < size; i += aFew) {
            List<T> subList = list.subList(i, Math.min((i + aFew), size));
            arrayList.add(subList);
        }
        return arrayList;
    }

    /**
     * 根据集合对象中指定的字段去重复
     *
     * @param keyExtractor keyExtractor
     * @param <T>          t
     * @return Predicate
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

}
