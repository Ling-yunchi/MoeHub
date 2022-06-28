package tv.moehub.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tv.moehub.annotation.Login;
import tv.moehub.bean.LikeVideoBean;
import tv.moehub.entity.LikeVideo;
import tv.moehub.model.BaseResult;
import tv.moehub.service.LikeService;

import javax.validation.Valid;

@RestController
@RequestMapping("/like")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LikeController {
    private final LikeService likeService;

    @Login
    @GetMapping("/setLike")
    public BaseResult<Void> setLikeVideo(@RequestParam String videoId) {
        BaseResult<Void> result = new BaseResult<>();
        likeService.setLikeVideo(videoId, result);
        return result;
    }

    @Login
    @GetMapping("/cancelLike")
    public BaseResult<Void> cancelLikeVideo(@RequestParam String videoId) {
        BaseResult<Void> result = new BaseResult<>();
        likeService.cancelLikeVideo(videoId, result);
        return result;
    }

    @GetMapping("/countLike")
    public BaseResult<Integer> countLikeVideo(@RequestParam String videoId){
        BaseResult<Integer> result = new BaseResult<>();
        likeService.countLikeVideo(videoId, result);
        return result;
    }
}
