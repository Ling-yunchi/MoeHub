package tv.moehub.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tv.moehub.model.BaseResult;
import tv.moehub.service.FileService;

/**
 * @author wangrong
 * @date 2022/6/22 14:22
 */
@RestController
@RequestMapping("/file")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    public BaseResult<String> upload(@RequestPart(value = "file", required = false) MultipartFile files) {
        BaseResult<String> result = new BaseResult<>();
        fileService.upload(files, result);
        return result;
    }
}
