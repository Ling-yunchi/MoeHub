package tv.moehub.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tv.moehub.entity.Video;

import java.util.List;

/**
 * @author wangrong
 * @date 2022/6/20 16:43
 */
public interface VideoDao extends JpaRepository<Video, String> {
    Video queryVideoById(String videoId);

    List<Video> findByTitleLike(String title);
}
