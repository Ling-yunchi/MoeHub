package tv.moehub.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tv.moehub.bean.UserBean;
import tv.moehub.dao.UserDao;
import tv.moehub.entity.User;
import tv.moehub.model.BaseResult;
import tv.moehub.model.UserResult;

/**
 * @author wangrong
 * @date 2022/6/20 15:35
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserDao userDao;

    public void getUserInfo(String userId, BaseResult<User> result) {
        User user = userDao.queryUserById(userId);
        if (user == null) {
            result.construct(false, "用户不存在");
            return;
        }
        result.construct(true, "查询成功", user);
    }


    public void register(UserBean userBean, BaseResult<UserResult> result) {
        User u = userDao.findByUsername(userBean.getUsername());
        if (u != null) {
            result.construct(false, "用户已存在");
            return;
        }

        User user = new User();
        BeanUtils.copyProperties(userBean, user);
        userDao.save(user);

        result.construct(true, "注册成功", new UserResult(user));
    }
}
