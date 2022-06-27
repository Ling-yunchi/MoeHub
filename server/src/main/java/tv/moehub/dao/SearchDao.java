package tv.moehub.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tv.moehub.entity.Search;
import tv.moehub.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * @author wangkunling
 * @date 2022/6/22 11:09
 */
public interface SearchDao extends JpaRepository<Search, String> {
    Search findSearchByStatement(String statement);

    @Query(value = "select * from search order by `count` desc limit 0,?1", nativeQuery = true)
    List<Search> findTopOrderByCount(Integer limit);
}
