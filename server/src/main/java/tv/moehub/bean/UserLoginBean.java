package tv.moehub.bean;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author wangrong
 * @date 2022/6/23 11:06
 */
@Data
public class UserLoginBean {
    @NotBlank(message = "用户名不能为空")
    @Length(min = 3, max = 20, message = "用户名长度必须在3-20之间")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 20, message = "密码长度必须在6-20之间")
    private String password;
}
