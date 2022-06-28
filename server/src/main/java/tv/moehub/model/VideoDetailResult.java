package tv.moehub.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author wangrong
 * @date 2022/6/28 14:18
 */
@Data
@AllArgsConstructor
public class VideoDetailResult {
    private String id;
    private String title;
    private String description;
    private Integer length;
    private String coverUrl;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createAt;
}
