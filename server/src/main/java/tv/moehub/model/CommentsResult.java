package tv.moehub.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import tv.moehub.entity.Comments;

import java.util.Date;

@Data
@AllArgsConstructor
public class CommentsResult {
    private String id;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createAt;
    private String userId;
    private String nickname;
    private String avatar;
}
