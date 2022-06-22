package tv.moehub.controller;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tv.moehub.dao.VideoDao;
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

    @GetMapping("/searchVideo")
    public BaseResult<List<Video>> searchVideo(@RequestParam String videoTitle) {
        BaseResult<List<Video>> result = new BaseResult<>();
        videoService.searchVideo(videoTitle, result);
        return result;
    }
}
