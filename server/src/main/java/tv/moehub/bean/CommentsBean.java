package tv.moehub.bean;

import lombok.Data;

import java.util.Date;

@Data
public class CommentsBean {
    private String id;
    private String content;//内容
    private String userId;
    private String videoId;
    private Date time;
//    private int likeNum;//点赞

}
