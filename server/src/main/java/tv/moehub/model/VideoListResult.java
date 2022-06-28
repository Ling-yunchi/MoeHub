package tv.moehub.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author wangrong
 * @date 2022/6/28 10:59
 */
@Data
@AllArgsConstructor
public class VideoListResult {
    private String id;
    private String title;
    private Integer length;
    private String coverUrl;
    private Integer views;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createAt;
    private String authorId;
    private String authorName;
    private String avatarUrl;
}
