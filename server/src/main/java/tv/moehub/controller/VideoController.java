package tv.moehub.controller;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tv.moehub.entity.Video;
import tv.moehub.model.BaseResult;
import tv.moehub.service.VideoService;

import java.util.List;

@RestController
@RequestMapping("/video")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VideoController {
    private final VideoService videoService;

    @GetMapping("/getVideoInfo")
    public BaseResult<Video> getVideoInfo(@RequestParam String videoId) {
        BaseResult<Video> result = new BaseResult<>();
        videoService.getVideoInfo(videoId, result);
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
}
