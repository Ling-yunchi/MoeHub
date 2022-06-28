package tv.moehub.bean;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author wangrong
 * @date 2022/6/27 17:01
 */
@Data
public class VideoBean {
    @NotBlank(message = "视频名称不能为空")
    @Length(max = 50, message = "标题长度不能超过50")
    private String title;
    private String description;
    @NotBlank(message = "上传视频不能为空")
    private String videoPrefix;
    @NotBlank(message = "上传封面不能为空")
    private String coverPrefix;
    @NotNull(message = "视频长度不能为空")
    @Min(value = 1, message = "视频长度不能小于1")
    private Integer length;
}
