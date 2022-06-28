package tv.moehub.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tv.moehub.entity.Comments;
import tv.moehub.model.CommentsResult;

public interface CommentsDao extends JpaRepository<Comments, String> {

    @Query(value = "select new tv.moehub.model.CommentsResult(c.id, c.content, c.createAt, u.id, u.nickname, u.avatar) " +
            "from Comments c inner join User u on c.userId = u.id " +
            "where c.videoId = ?1 " +
            "order by c.createAt desc",
            countQuery = "select count(c) from Comments c where c.videoId = ?1")
    Page<CommentsResult> queryAllByVideoId(String videoId, Pageable pageable);

    Comments queryCommentsById(String id);
}
