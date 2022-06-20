package tv.moehub.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tv.moehub.bean.UserBean;
import tv.moehub.entity.User;
import tv.moehub.model.BaseResult;
import tv.moehub.model.UserResult;
import tv.moehub.service.UserService;

/**
 * @author wangrong
 * @date 2022/6/20 15:24
 */
@RestController
@RequestMapping("/user")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public BaseResult<User> getUserInfo(@PathVariable String userId) {
        BaseResult<User> result = new BaseResult<>();
        userService.getUserInfo(userId, result);
        return result;
    }

    @PostMapping("/register")
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
}
