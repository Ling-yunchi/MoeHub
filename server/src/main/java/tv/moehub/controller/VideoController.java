package tv.moehub.controller;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tv.moehub.bean.VideoBean;
import tv.moehub.entity.Video;
import tv.moehub.model.BasePageResult;
import tv.moehub.model.BaseResult;
import tv.moehub.model.VideoResult;
import tv.moehub.service.VideoService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/video")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VideoController {
    private final VideoService videoService;

    @GetMapping("/queryVideoById")
    public BaseResult<VideoResult> queryVideoById(@RequestParam String videoId) {
        BaseResult<VideoResult> result = new BaseResult<>();
        videoService.queryVideoById(videoId, result);
        return result;
    }

    @GetMapping("/searchVideoByTitle")
    public BasePageResult<VideoResult> searchVideoByTitle(@RequestParam String videoTitle, @RequestParam int pageNum, @RequestParam int pageSize) {
        BasePageResult<VideoResult> result = new BasePageResult<>();
        videoService.searchVideoByTitle(videoTitle, result, pageNum, pageSize);
        return result;
    }

    @GetMapping("/searchVideoByAuthor")
    public BasePageResult<VideoResult> searchVideoByAuthor(@RequestParam String nickname, @RequestParam int pageNum, @RequestParam int pageSize) {
        BasePageResult<VideoResult> result = new BasePageResult<>();
        videoService.searchVideoByAuthor(nickname, result, pageNum, pageSize);
        return result;
    }

    @PostMapping("/add")
    public BaseResult<Void> add(@RequestBody @Valid VideoBean videoBean) {
        BaseResult<Void> result = new BaseResult<>();
        videoService.add(videoBean, result);
        return result;
    }

    @PostMapping("/upload")
    public BaseResult<String> upload(@RequestPart MultipartFile video) {
        BaseResult<String> result = new BaseResult<>();
        videoService.upload(video, result);
        return result;
    }
}
