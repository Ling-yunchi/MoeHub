package tv.moehub.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import tv.moehub.entity.Favorite;
import tv.moehub.model.BaseResult;
import tv.moehub.model.VideoResult;
import tv.moehub.service.FavoriteService;

import java.util.List;

@RestController
@RequestMapping("/favorite")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FavoriteController {
    private final FavoriteService favoriteService;

    @GetMapping("/isFavorite")
    public BaseResult<Favorite> isFavorite(@RequestParam String userId, @RequestParam String videoId) {
        BaseResult<Favorite> result = new BaseResult<>();
        favoriteService.isFavorite(userId, videoId, result);
        return result;
    }

    @GetMapping("/showMyFavorite")
    public BaseResult<Page<VideoResult>> showMyFavorite(@RequestParam String userId, @RequestParam int pageNum, @RequestParam int pageSize) {
        BaseResult<Page<VideoResult>> result = new BaseResult<>();
        favoriteService.showMyFavorite(userId, result, pageNum, pageSize);
        return result;
    }
}
