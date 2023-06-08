package tv.moehub.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tv.moehub.entity.LastPlayLocation;

public interface LastPlayLocationDao extends JpaRepository<LastPlayLocation, String> {

    @Query("select l " +
            "from LastPlayLocation l " +
            "where l.videoId = ?1 and l.userId = ?2")
    LastPlayLocation findLastPositionByVideoIdAndUserId(String videoId, String userId);
}
