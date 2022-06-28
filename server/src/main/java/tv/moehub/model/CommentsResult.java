package tv.moehub.model;

import lombok.Data;
import tv.moehub.dao.CommentsDao;
import tv.moehub.dao.UserDao;
import tv.moehub.entity.Comments;

import java.util.Date;

@Data
public class CommentsResult {
    private final CommentsDao commentsDao;
    private final UserDao userDao;

    private String id;
    private String userId;
    private String content;
    private Date time;
    private String userName;
    private String avatar;




    public CommentsResult(CommentsDao commentsDao, UserDao userDao, Comments comments) {
        //不知道怎么像service一样用Dao,构造有点麻烦

        this.commentsDao = commentsDao;
        this.userDao = userDao;

        this.id = comments.getId();
        this.userId = comments.getUserId();
        this.userName= this.userDao.queryUserById(comments.getUserId()).getUsername();
        this.content = comments.getContent();
        this.time = comments.getTime();
        this.avatar = this.userDao.queryUserById(comments.getUserId()).getAvatar();

    }
}
