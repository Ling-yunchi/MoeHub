package tv.moehub.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tv.moehub.entity.Video;
import tv.moehub.model.VideoResult;

import java.util.List;

/**
 * @author wangrong
 * @date 2022/6/20 16:43
 */
public interface VideoDao extends JpaRepository<Video, String> {
    @Query(value = "select new tv.moehub.model.VideoResult(v.id, v.cover, v.length, v.title, v.authorId, u.avatar, u.nickname, 0) " +
            "from Video v join User u on v.authorId = u.id " +
            "where v.id = ?1")
    VideoResult queryVideoById(String videoId);

    @Query(value = "select new tv.moehub.model.VideoResult(v.id, v.cover, v.length, v.title, v.authorId, u.avatar, u.nickname, 0) " +
            "from Video v join User u on v.authorId = u.id " +
            "where v.title like ?1")
    List<VideoResult> findByTitleLike(String title);

    @Query(value = "select new tv.moehub.model.VideoResult(v.id, v.cover, v.length, v.title, v.authorId, u.avatar, u.nickname, 0) " +
            "from Video v join User u on v.authorId = u.id " +
            "where u.nickname = ?1")
    List<VideoResult> findByAuthorId(String authorId);
}
