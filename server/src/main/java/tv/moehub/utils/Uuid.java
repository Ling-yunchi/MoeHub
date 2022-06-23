package tv.moehub.utils;

/**
 * @author wangrong
 * @date 2022/6/22 16:32
 */
public class Uuid {
    public static String getUuid() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }
}
