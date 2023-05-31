package tv.moehub.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tv.moehub.entity.Favorite;
import tv.moehub.model.VideoListResult;

import java.util.List;

public interface FavoriteDao extends JpaRepository<Favorite, String> {
    Favorite queryByUserIdAndVideoId(String userId, String videoId);

    @Override
    void deleteById(String favoriteId);

    Integer countByVideoId(String videoId);

    @Query("select new tv.moehub.model.VideoListResult(v.id, v.title, v.length, v.coverPrefix, v.views, v.createAt, v.authorId, u.nickname, u.avatar, v.category) " +
            "from Video v inner join Favorite f on v.id = f.videoId inner join User u on f.userId = u.id " +
            "where f.userId = ?1")
    Page<VideoListResult> queryByUserId(String userId, Pageable pageable);
}
