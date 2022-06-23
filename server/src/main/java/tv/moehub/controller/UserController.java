package tv.moehub.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tv.moehub.annotation.Login;
import tv.moehub.bean.UserBean;
import tv.moehub.bean.UserLoginBean;
import tv.moehub.entity.User;
import tv.moehub.model.BaseResult;
import tv.moehub.model.UserResult;
import tv.moehub.service.UserService;

import javax.validation.Valid;

import static tv.moehub.utils.Const.User.*;

/**
 * @author wangrong
 * @date 2022/6/20 15:24
 */
@RestController
@RequestMapping(ROOT)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;

    @GetMapping("/info")
    public BaseResult<User> getUserInfo(@RequestParam String userId) {
        BaseResult<User> result = new BaseResult<>();
        userService.getUserInfo(userId, result);
        return result;
    }

    @PostMapping(REGISTER)
    public BaseResult<UserResult> register(@RequestBody UserBean userBean) {
        BaseResult<UserResult> result = new BaseResult<>();
        userService.register(userBean, result);
        return result;
    }

//    @PutMapping("/{userId}")
//    public BaseResult<UserResult> update(@PathVariable String userId, @RequestBody UserBean userBean) {
//        BaseResult<UserResult> result = new BaseResult<>();
//        userService.update(userBean, result);
//        return result;
//    }
//
//    @DeleteMapping("/delete")
//    public BaseResult<UserResult> delete(@RequestParam String userId) {
//        BaseResult<UserResult> result = new BaseResult<>();
//        userService.delete(userId, result);
//        return result;
//    }

    @Login
    @GetMapping("/self")
    public BaseResult<UserResult> getSelf() {
        BaseResult<UserResult> result = new BaseResult<>();
        userService.self(result);
        return result;
    }

    @PostMapping(LOGIN)
    public BaseResult<Void> login(@Valid @RequestBody UserLoginBean userLoginBean) {
        BaseResult<Void> result = new BaseResult<>();
        userService.login(userLoginBean, result);
        return result;
    }

    @Login
    @PostMapping("/logout")
    public BaseResult<Void> logout() {
        BaseResult<Void> result = new BaseResult<>();
        userService.logout(result);
        return result;
    }

    @Login
    @PostMapping("/avatar")
    public BaseResult<String> uploadAvatar(@RequestParam MultipartFile avatar) {
        BaseResult<String> result = new BaseResult<>();
        userService.uploadAvatar(avatar, result);
        return result;
    }
}
