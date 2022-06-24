package tv.moehub.model;

import lombok.Data;
import tv.moehub.entity.Comments;

import java.util.Date;

@Data
public class CommentsResult {
    private String id;
    private String userId;
    private String videoId;
    private String content;
    private Date time;

    public CommentsResult(Comments comments) {
        this.id = comments.getId();
        this.userId = comments.getUserId();
        this.videoId = comments.getVideoId();
        this.content = comments.getContent();
        this.time = comments.getTime();

    }
}
