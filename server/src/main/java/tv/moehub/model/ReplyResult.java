package tv.moehub.model;

import lombok.Data;
import tv.moehub.entity.Reply;

import java.util.Date;

@Data
public class ReplyResult {   //对评论的回复
    private String id;
    private String userId;
    private String commentsId;
    private String content;//内容
    private Date time;

    public ReplyResult(Reply reply) {
        this.id = reply.getId();
        this.userId = reply.getUserId();
        this.commentsId = reply.getCommentsId();
        this.content = reply.getContent();
        this.time = reply.getTime();
    }

}
