package tv.moehub.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tv.moehub.entity.LikeVideo;

public interface LikeVideoDao extends JpaRepository<LikeVideo, String> {

    LikeVideo queryLikeVideoByUserIdAndVideoId(String userId, String videoId);

    int countLikeVideoByVideoId(String videoId);

}
