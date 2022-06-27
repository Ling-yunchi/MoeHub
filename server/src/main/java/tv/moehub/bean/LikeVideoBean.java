package tv.moehub.bean;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LikeVideoBean {
    //TODO 参数校验
    @NotBlank(message = "用户Id不能为空")
    private String userId;
    @NotBlank(message = "视频Id不能为空")
    private String videoId;
}
