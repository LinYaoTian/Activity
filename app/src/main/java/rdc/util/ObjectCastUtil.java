package rdc.util;

/**
 * Created by asus on 18-1-24.
 */

public class ObjectCastUtil {
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) throws ClassCastException {
        return (T) obj;
    }
}
