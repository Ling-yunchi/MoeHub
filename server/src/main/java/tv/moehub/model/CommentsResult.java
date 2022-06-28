package tv.moehub.model;

import lombok.Data;
import tv.moehub.entity.Comments;

import java.util.Date;

@Data
public class CommentsResult {

    private String id;
    private String userId;
    private String content;
    private Date time;
    private String userName;
    private String avatar;




    public CommentsResult(Comments comments) {

        this.id = comments.getId();
        this.userId = comments.getUserId();
        this.content = comments.getContent();
        this.time = comments.getTime();
    }
}
