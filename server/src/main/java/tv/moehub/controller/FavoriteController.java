package tv.moehub.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tv.moehub.entity.Favorite;
import tv.moehub.model.BaseResult;
import tv.moehub.service.FavoriteService;

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
}
