package tv.moehub.bean;

import lombok.Data;

/**
 * @author wangrong
 * @date 2022/6/20 15:55
 */
@Data
public class UserBean {
    private String id;
    private String username;
    private String nickname;
    private String password;
    private String avatar;
    private String sex;
    private String email;
    private String phone;
}
