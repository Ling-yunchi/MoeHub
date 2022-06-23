package tv.moehub.config.shiro;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import tv.moehub.dao.UserDao;

/**
 * @author wangrong
 * @date 2022/6/22 22:39
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserDao userDao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("---------- 认证 ----------");
        var token = (UsernamePasswordToken) authenticationToken;
        var username = token.getUsername();
        var password = String.valueOf(token.getPassword());
        var user = userDao.findByUsername(username);
        if (user == null) {
            throw new UnknownAccountException("用户不存在");
        }
        // 使用SimpleAuthenticationInfo进行认证
        // 参数1：principal，参数2：credentials，参数3：realmName
        // 这里传入的principal可以通过Subject subject = SecurityUtils.getSubject().getPrincipal();获取
        return new SimpleAuthenticationInfo(user.getId(), password, getName());
    }
}
