package tv.moehub.model;

import lombok.Data;
import tv.moehub.entity.Reply;

import java.util.Date;

@Data
public class ReplyResult {   //对评论的回复
    private String id;
    private String userId;
    private String content;//内容
    private String commentsId;
    private Date time;

    public ReplyResult(Reply reply){
        this.commentsId=reply.getCommentsId();
        this.content=reply.getContent();
        this.id=reply.getId();
        this.userId=reply.getUserId();
        this.time=reply.getTime();
    }

}
