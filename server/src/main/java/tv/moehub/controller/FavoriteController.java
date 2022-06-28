package tv.moehub.controller;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import tv.moehub.annotation.Login;
import tv.moehub.entity.Favorite;
import tv.moehub.model.BasePageResult;
import tv.moehub.model.BaseResult;
import tv.moehub.model.VideoListResult;
import tv.moehub.model.VideoResult;
import tv.moehub.service.FavoriteService;

import java.util.List;

@RestController
@RequestMapping("/favorite")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FavoriteController {
    private final FavoriteService favoriteService;

    @Login
    @GetMapping("/isFavorite")
    public BaseResult<Void> isFavorite(@RequestParam String videoId) {
        BaseResult<Void> result = new BaseResult<>();
        favoriteService.isFavorite(videoId, result);
        return result;
    }

    @GetMapping("/showMyFavorite")
    public BasePageResult<VideoListResult> showMyFavorite(@RequestParam String userId, @RequestParam int pageNum, @RequestParam int pageSize) {
        BasePageResult<VideoListResult> result = new BasePageResult<>();
        favoriteService.showMyFavorite(userId, result, pageNum, pageSize);
        return result;
    }
}
