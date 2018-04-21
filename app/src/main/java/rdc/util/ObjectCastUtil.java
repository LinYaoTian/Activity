package rdc.util;

/**
 * Created by asus on 18-1-24.
 */

public class ObjectCastUtil {
    /**
     * 类型转换
     * @param obj
     * @param <T>
     * @return
     * @throws ClassCastException
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) throws ClassCastException {
        return (T) obj;
    }
}
