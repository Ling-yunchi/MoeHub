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
    @PostMapping("/setLike")
    public BaseResult<LikeVideo> setLikeVideo(@Valid @RequestBody LikeVideoBean likeVideoBean){
        BaseResult<LikeVideo> result = new BaseResult<>();
        likeService.setLikeVideo(likeVideoBean, result);
        return result;
    }

    @Login
    @PostMapping("/cancelLike")
    public BaseResult<LikeVideo> cancelLikeVideo(@Valid @RequestBody LikeVideoBean likeVideoBean){
        BaseResult<LikeVideo> result = new BaseResult<>();
        likeService.cancelLikeVideo(likeVideoBean, result);
        return result;
    }

    @PostMapping("/countLike")
    public BaseResult<Integer> countLikeVideo(@RequestParam String videoId){
        BaseResult<Integer> result = new BaseResult<>();
        likeService.countLikeVideo(videoId, result);
        return result;
    }
}
