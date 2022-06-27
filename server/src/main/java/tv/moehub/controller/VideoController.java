package tv.moehub.controller;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import tv.moehub.entity.Video;
import tv.moehub.model.BaseResult;
import tv.moehub.model.VideoResult;
import tv.moehub.service.VideoService;

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
    public BaseResult<Page<VideoResult>> searchVideoByTitle(@RequestParam String videoTitle, @RequestParam int pageNum, @RequestParam int pageSize) {
        BaseResult<Page<VideoResult>> result = new BaseResult<>();
        videoService.searchVideoByTitle(videoTitle, result, pageNum, pageSize);
        return result;
    }

    @GetMapping("/searchVideoByAuthor")
    public BaseResult<Page<VideoResult>> searchVideoByAuthor(@RequestParam String nickname, @RequestParam int pageNum, @RequestParam int pageSize) {
        BaseResult<Page<VideoResult>> result = new BaseResult<>();
        videoService.searchVideoByAuthor(nickname, result, pageNum, pageSize);
        return result;
    }
}
