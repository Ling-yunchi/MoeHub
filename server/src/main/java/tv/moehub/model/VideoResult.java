package tv.moehub.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author wangrong
 * @date 2022/6/28 15:39
 */
@Builder
@Data
public class VideoResult {
    private String id;
    private String title;
    private String description;
    private Integer length;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createAt;
    private String coverUrl;
    private String videoUrl;
    private String authorId;
    private String authorName;
    private String authorAvatar;
    private Integer views;
    private Integer likes;
    private Integer favorites;
    private Boolean isFavorite;
    private Boolean isLiked;
}
