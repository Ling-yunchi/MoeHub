package tv.moehub.bean;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author wangrong
 * @date 2022/6/20 15:55
 */
@Data
public class UserBean {
    @NotBlank(message = "用户名不能为空")
    private String username;
    private String nickname;
    private String password;
    private String sex;
    private String email;
}
