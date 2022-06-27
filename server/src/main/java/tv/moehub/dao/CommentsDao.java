package tv.moehub.dao;

import com.github.pagehelper.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tv.moehub.entity.Comments;

public interface CommentsDao extends JpaRepository<Comments, String> {

    @Query(value = "select c from Comments c where c.videoId = ?1",
            countQuery = "select count(c) from Comments c where c.videoId = ?1")
    Page<Comments> queryAllByVideoId(String videoId, Pageable pageable);

    Comments queryCommentsById(String id);

    void deleteById(String id);

}
