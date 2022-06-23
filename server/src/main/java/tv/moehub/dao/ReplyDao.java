package tv.moehub.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tv.moehub.entity.Reply;

import java.util.List;

public interface ReplyDao extends JpaRepository<Reply,String> {

    @Query("select r from Reply r where r.commentsId = ?1")
    List<Reply> queryAllByCommentsId(String commentsId); //根据评论id找回复
                                                    //应返回List(待改进)
    void deleteById (String id);//删回复
    Reply queryReplyById(String id);

}
