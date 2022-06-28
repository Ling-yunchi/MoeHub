package tv.moehub.controller;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tv.moehub.annotation.Login;
import tv.moehub.bean.VideoBean;
import tv.moehub.entity.Video;
import tv.moehub.model.BasePageResult;
import tv.moehub.model.BaseResult;
import tv.moehub.model.VideoDetailResult;
import tv.moehub.model.VideoListResult;
import tv.moehub.service.VideoService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/video")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VideoController {
    private final VideoService videoService;

    @GetMapping("/queryVideoById")
    public BaseResult<Video> queryVideoById(@RequestParam String videoId) {
        BaseResult<Video> result = new BaseResult<>();
        videoService.queryVideoById(videoId, result);
        return result;
    }

    @GetMapping("/searchVideoByTitle")
    public BaseResult<List<Video>> searchVideoByTitle(@RequestParam String videoTitle) {
        BaseResult<List<Video>> result = new BaseResult<>();
        videoService.searchVideoByTitle(videoTitle, result);
        return result;
    }

    @GetMapping("/searchVideoByAuthor")
    public BaseResult<List<Video>> searchVideoByAuthor(@RequestParam String nickname) {
        BaseResult<List<Video>> result = new BaseResult<>();
        videoService.searchVideoByAuthor(nickname, result);
        return result;
    }

    @GetMapping("/getUserVideo")
    public BasePageResult<VideoListResult> getUserVideo(@RequestParam String userId, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        BasePageResult<VideoListResult> result = new BasePageResult<>();
        videoService.getUserVideo(userId, pageNum, pageSize, result);
        return result;
    }

    @Login
    @PostMapping("/add")
    public BaseResult<Void> add(@RequestBody @Valid VideoBean videoBean) {
        BaseResult<Void> result = new BaseResult<>();
        videoService.add(videoBean, result);
        return result;
    }

    @Login
    @GetMapping("/delete")
    public BaseResult<Void> delete(@RequestParam String videoId) {
        BaseResult<Void> result = new BaseResult<>();
        videoService.delete(videoId, result);
        return result;
    }

    @Login
    @GetMapping("/getMyVideoDetails")
    public BaseResult<List<VideoDetailResult>> getVideoDetails() {
        BaseResult<List<VideoDetailResult>> result = new BaseResult<>();
        videoService.getVideoDetails(result);
        return result;
    }

    @Login
    @PostMapping("/uploadTemp")
    public BaseResult<String> uploadTemp(@RequestPart MultipartFile file) {
        BaseResult<String> result = new BaseResult<>();
        videoService.uploadTemp(file, result);
        return result;
    }
}
