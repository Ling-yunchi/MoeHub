package tv.moehub.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tv.moehub.entity.User;

/**
 * @author wangrong
 * @date 2022/6/20 15:44
 */
public interface UserDao extends JpaRepository<User, String> {
    User queryUserById(String userId);

    User findByUsername(String username);
}
