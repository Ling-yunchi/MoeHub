package tv.moehub.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author wangrong
 * @date 2022/6/20 16:22
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "video")
public class Video {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    private String title;//名称
    private String description;//简介
    private String authorId;//作者id
    private Date time;//时长
    private Date releaseTime;//发布时间
    private String coverUrl;//封面
    private String videoUrl;//视频内容
}
