package tv.moehub.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tv.moehub.dao.FavoriteDao;
import tv.moehub.entity.Favorite;
import tv.moehub.model.BaseResult;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FavoriteService {
    private final FavoriteDao favoriteDao;

    public void isFavorite(String userId, String videoId, BaseResult<Favorite> result) {
        Favorite favorite = favoriteDao.queryByUidAndVid(userId, videoId);
        if (favorite == null) {
            result.construct(false, "未收藏");
            Favorite f = new Favorite(userId, videoId);
            favoriteDao.save(f);
        } else {
            result.construct(true, "已收藏", favorite);
            favoriteDao.deleteById(favorite.getId());
        }
    }
}
