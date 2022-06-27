package tv.moehub.model;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wangkunling
 * @date 2022/6/24 11:36
 */
@Data
@AllArgsConstructor
public class VideoResult {
    String id;
    String cover;
    Integer length;
    String title;
    String authorId;
    String avatar;
    String authorName;
    Integer views;
}
