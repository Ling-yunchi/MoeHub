package tv.moehub.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tv.moehub.entity.Reply;

import java.util.List;

public interface ReplyDao extends JpaRepository<Reply, String> {

    List<Reply> queryAllByCommentsId(String commentsId); //根据评论id找回复

    Reply queryReplyById(String id);

    void deleteById(String id);//删回复

}
