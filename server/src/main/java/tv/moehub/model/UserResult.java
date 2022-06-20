package tv.moehub.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import tv.moehub.entity.User;

/**
 * @author wangrong
 * @date 2022/6/20 15:57
 */
@Data
public class UserResult {
    private String id;
    private String username;
    private String nickname;
    private String avatar;
    private String sex;
    private String email;
    private String phone;

    public UserResult(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.avatar = user.getAvatar();
        this.sex = user.getSex();
        this.email = user.getEmail();
        this.phone = user.getPhone();
    }
}
