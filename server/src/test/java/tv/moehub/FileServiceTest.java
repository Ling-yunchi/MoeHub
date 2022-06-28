package tv.moehub;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tv.moehub.service.FileService;

/**
 * @author wangrong
 * @date 2022/6/27 15:07
 */
@SpringBootTest
public class FileServiceTest {
    @Autowired
    FileService fileService;

    @SneakyThrows
    @Test
    public void testGetObjectUrl() {
        System.out.println(fileService.getFileUrl("avatar/08e86b8091f8407485482985788482fc.svg"));
    }

    @Test
    @SneakyThrows
    public void testMoveFile() {
        fileService.moveFile("temp/b0a5fedabff14b48bfd880ffd73a4ec6.webp", "cover/b0a5fedabff14b48bfd880ffd73a4ec6.webp");
    }
}
