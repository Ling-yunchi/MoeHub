package tv.moehub.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tv.moehub.entity.Favorite;

public interface FavoriteDao extends JpaRepository<Favorite, String> {
    Favorite queryByUserIdAndVideoId(String userId, String videoId);

    void deleteById(String favoriteId);
}