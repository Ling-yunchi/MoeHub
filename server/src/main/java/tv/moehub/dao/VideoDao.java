package tv.moehub.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tv.moehub.entity.Video;
import tv.moehub.model.VideoDetailResult;
import tv.moehub.model.VideoResult;
import tv.moehub.model.VideoListResult;

import java.nio.channels.FileChannel;
import java.util.List;

/**
 * @author wangrong
 * @date 2022/6/20 16:43
 */
public interface VideoDao extends JpaRepository<Video, String> {

    @Query("select new tv.moehub.model.VideoListResult(v.id, v.title, v.length, v.coverPrefix, v.views, v.createAt, v.authorId, u.nickname, u.avatar) " +
            "from Video v inner join User u on v.authorId = u.id order by v.views desc ")
    Page<VideoListResult> showHot(Pageable pageable);

    @Query("select new tv.moehub.model.VideoListResult(v.id, v.title, v.length, v.coverPrefix, v.views, v.createAt, v.authorId, u.nickname, u.avatar) " +
            "from Video v join User u on v.authorId = u.id " +
            "where v.id = ?1")
    VideoListResult queryVideoById(String videoId);

    @Query("select new tv.moehub.model.VideoListResult(v.id, v.title, v.length, v.coverPrefix, v.views, v.createAt, v.authorId, u.nickname, u.avatar) " +
            "from Video v join User u on v.authorId = u.id " +
            "where v.title like ?1")
    Page<VideoListResult> findByTitleLike(String title, Pageable pageable);

    @Query("select new tv.moehub.model.VideoListResult(v.id, v.title, v.length, v.coverPrefix, v.views, v.createAt, v.authorId, u.nickname, u.avatar) " +
            "from Video v inner join User u on v.authorId = u.id " +
            "where v.authorId = ?1")
    Page<VideoListResult> findByAuthorIdPage(String authorId, Pageable pageable);

    @Query("select new tv.moehub.model.VideoDetailResult(v.id, v.title, v.description, v.length, v.coverPrefix, v.createAt) " +
            "from Video v " +
            "where v.authorId = ?1")
    List<VideoDetailResult> findVideoDetailByAuthorId(String userId);

    @Query("select new tv.moehub.model.VideoListResult(v.id, v.title, v.length, v.coverPrefix, v.views, v.createAt, v.authorId, u.nickname, u.avatar) " +
            "from Video v inner join User u on v.authorId = u.id")
    Page<VideoListResult> findAllPage(Pageable pageable);
}
