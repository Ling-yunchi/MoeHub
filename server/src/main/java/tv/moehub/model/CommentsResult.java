package tv.moehub.model;

import lombok.Data;
import tv.moehub.entity.Comments;

import java.util.Date;

@Data
public class CommentsResult {
    private String id;
    private String content;
    private String userId;
    private String videoId;
    private Date time;
    private int likeNum;//点赞

    public CommentsResult(Comments comments){
        this.id=comments.getId();
        this.content=comments.getContent();
        this.userId=comments.getUserId();
        this.videoId=comments.getVideoId();
        this.time=comments.getTime();
        this.likeNum=comments.getLikeNum();

    }
}
