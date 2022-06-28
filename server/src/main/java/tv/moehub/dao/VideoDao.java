package tv.moehub.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tv.moehub.entity.Video;

import java.util.List;

/**
 * @author wangrong
 * @date 2022/6/20 16:43
 */
public interface VideoDao extends JpaRepository<Video, String> {
    Video queryVideoById(String videoId);

    List<Video> findByTitleLike(String title);


    List<Video> findByAuthorId(String authorId);

    @Query("select v from Video v where v.authorId = ?1")
    Page<Video> findByAuthorIdPageable(String userId, Pageable pageable);
}
