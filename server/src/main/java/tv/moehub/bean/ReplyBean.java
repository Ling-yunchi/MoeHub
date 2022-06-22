package tv.moehub.bean;

import lombok.Data;

import java.util.Date;

@Data
public class ReplyBean {   //对评论的回复
    private String id;
    private String userId;
    private String content;//内容
    private Date time;
    private String commentsId;

}
