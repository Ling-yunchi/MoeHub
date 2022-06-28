package tv.moehub.utils;

/**
 * @author wangrong
 * @date 2022/6/27 15:18
 */
public class FileUtil {
    public static String getFileExtension(String fileOriginName) {
        return fileOriginName.substring(fileOriginName.lastIndexOf(".") + 1);
    }
}
