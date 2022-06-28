package tv.moehub.controller;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tv.moehub.annotation.Login;
import tv.moehub.bean.VideoBean;
import tv.moehub.model.*;
import tv.moehub.service.VideoService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/video")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VideoController {
    private final VideoService videoService;

//    @GetMapping("/queryVideoById")
//    public BaseResult<VideoResult> queryVideoById(@RequestParam String videoId) {
//        BaseResult<VideoResult> result = new BaseResult<>();
//        videoService.queryVideoById(videoId, result);
//        return result;
//    }

    @GetMapping("/hot")
    public BasePageResult<VideoListResult> showHot(@RequestParam int pageNum, @RequestParam int pageSize) {
        BasePageResult<VideoListResult> result = new BasePageResult<>();
        videoService.showHot(result, pageNum, pageSize);
        return result;
    }

    @GetMapping("/searchVideoByTitle")
    public BasePageResult<VideoListResult> searchVideoByTitle(@RequestParam String videoTitle, @RequestParam int pageNum, @RequestParam int pageSize) {
        BasePageResult<VideoListResult> result = new BasePageResult<>();
        videoService.searchVideoByTitle(videoTitle, result, pageNum, pageSize);
        return result;
    }

    @GetMapping("/searchVideoByAuthor")
    public BasePageResult<VideoListResult> searchVideoByAuthor(@RequestParam String nickname, @RequestParam int pageNum, @RequestParam int pageSize) {
        BasePageResult<VideoListResult> result = new BasePageResult<>();
        videoService.searchVideoByAuthor(nickname, result, pageNum, pageSize);
        return result;
    }

    @GetMapping("/getUserVideo")
    public BasePageResult<VideoListResult> getUserVideo(@RequestParam String userId, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        BasePageResult<VideoListResult> result = new BasePageResult<>();
        videoService.getUserVideo(userId, pageNum, pageSize, result);
        return result;
    }

    @GetMapping("/getAllVideo")
    public BasePageResult<VideoListResult> getAllVideo(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        BasePageResult<VideoListResult> result = new BasePageResult<>();
        videoService.getAllVideo(pageNum, pageSize, result);
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

    @GetMapping("/getVideoInfo")
    public BaseResult<VideoResult> getVideoInfo(@RequestParam String videoId) {
        BaseResult<VideoResult> result = new BaseResult<>();
        videoService.getVideoInfo(videoId, result);
        return result;
    }

    @GetMapping("/view")
    public BaseResult<Void> view(@RequestParam String videoId) {
        BaseResult<Void> result = new BaseResult<>();
        videoService.view(videoId, result);
        return result;
    }

}
