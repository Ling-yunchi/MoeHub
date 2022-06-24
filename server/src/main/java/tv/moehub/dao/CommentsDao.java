package tv.moehub.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tv.moehub.entity.Comments;

import java.util.List;

public interface CommentsDao extends JpaRepository<Comments, String> {

    List<Comments> queryAllByVideoId(String videoId);

    Comments queryCommentsById(String id);

    void deleteById(String id);

}
