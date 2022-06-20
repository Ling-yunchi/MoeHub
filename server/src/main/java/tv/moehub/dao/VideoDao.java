package tv.moehub.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tv.moehub.entity.Video;

/**
 * @author wangrong
 * @date 2022/6/20 16:43
 */
public interface VideoDao extends JpaRepository<Video, String> {
}
