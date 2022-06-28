package tv.moehub.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tv.moehub.entity.Video;
import tv.moehub.model.VideoDetailResult;
import tv.moehub.model.VideoListResult;

import java.util.List;

/**
 * @author wangrong
 * @date 2022/6/20 16:43
 */
public interface VideoDao extends JpaRepository<Video, String> {
    Video queryVideoById(String videoId);

    List<Video> findByTitleLike(String title);


    List<Video> findByAuthorId(String authorId);

    @Query("select new tv.moehub.model.VideoListResult(v.id, v.title, v.length, v.coverPrefix, v.views, v.createAt, v.authorId, u.nickname, u.avatar) " +
            "from Video v inner join User u on v.authorId = u.id " +
            "where v.authorId = ?1")
    Page<VideoListResult> findByAuthorIdPage(String authorId, Pageable pageable);

    @Query("select new tv.moehub.model.VideoDetailResult(v.id, v.title, v.description, v.length, v.coverPrefix, v.createAt) " +
            "from Video v " +
            "where v.authorId = ?1")
    List<VideoDetailResult> findVideoDetailByAuthorId(String userId);
}
