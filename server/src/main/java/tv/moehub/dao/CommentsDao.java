package tv.moehub.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tv.moehub.entity.Comments;

import java.util.List;

public interface CommentsDao extends JpaRepository<Comments,String> {

    List<Comments> findAllByVideoId(String videoId); //根据视频id找出视频的所有评论
                                                    //应返回List
    Comments queryCommentsById(String id);
    void deleteById (String id);//删评论

}
