package tv.moehub.bean;

import lombok.Data;

import java.util.Date;

@Data
public class CommentsBean {
    private String content;//内容
    private String videoId;
}
