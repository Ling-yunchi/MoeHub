package tv.moehub.service;

import lombok.AllArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tv.moehub.bean.UserBean;
import tv.moehub.bean.UserLoginBean;
import tv.moehub.dao.UserDao;
import tv.moehub.entity.User;
import tv.moehub.model.BaseResult;
import tv.moehub.model.UserResult;
import tv.moehub.utils.FileUtil;
import tv.moehub.utils.Uuid;

import java.util.Objects;

/**
 * @author wangrong
 * @date 2022/6/20 15:35
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserDao userDao;
    private final FileService fileService;

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

    public void uploadAvatar(MultipartFile avatar, BaseResult<String> result) {
        var userId = (String) SecurityUtils.getSubject().getPrincipal();
        var user = userDao.queryUserById(userId);
        if (user.getAvatar() != null && !user.getAvatar().isEmpty()) {
            var filePrefix = user.getAvatar();
            try {
                fileService.deleteFile(filePrefix);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        var filePrefix = "avatar/" + Uuid.getUuid() + "." + FileUtil.getFileExtension(Objects.requireNonNull(avatar.getOriginalFilename()));
        try {
            var url = fileService.uploadFile(avatar, filePrefix);
            user.setAvatar(url);
            userDao.save(user);
            result.construct(true, "上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.construct(false, "上传失败");
        }
    }

    public void login(UserLoginBean userLoginBean, BaseResult<Void> result) {
        System.out.println(userLoginBean);
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            result.construct(false, "用户已登录");
            return;
        }
        var token = new UsernamePasswordToken(userLoginBean.getUsername(), userLoginBean.getPassword());
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            result.construct(false, "用户不存在");
            return;
        } catch (IncorrectCredentialsException e) {
            result.construct(false, "密码错误");
            return;
        }
        result.construct(true, "登录成功");
    }

    public void logout(BaseResult<Void> result) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        result.construct(true, "登出成功");
    }

    public void self(BaseResult<UserResult> result) {
        var userId = (String) SecurityUtils.getSubject().getPrincipal();
        var user = userDao.queryUserById(userId);
        result.construct(true, "查询成功", new UserResult(user));
    }
}
