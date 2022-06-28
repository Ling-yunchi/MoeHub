package tv.moehub.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tv.moehub.dao.SearchDao;
import tv.moehub.entity.Favorite;
import tv.moehub.entity.Search;
import tv.moehub.model.BaseResult;

import java.util.List;

/**
 * @author wangkunling
 * @date 2022/6/22 14:14
 */
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SearchService {
    private final SearchDao searchDao;

    public void updateSearchCount(String statement, BaseResult<Void> result) {
        Search search = searchDao.findSearchByStatement(statement);
        if (search == null) {
            Search s = Search.builder()
                    .statement(statement)
                    .count(1)
                    .build();
            searchDao.save(s);
            result.construct(true, "已经记录该新搜索");
        } else {
            Search s = Search.builder()
                    .id(search.getId())
                    .statement(statement)
                    .count(search.getCount() + 1)
                    .build();
            searchDao.save(s);
            result.construct(true, "已经为该搜索增加搜索数");
        }
    }

    public void showHot(BaseResult<List<Search>> result) {
        List<Search> searchList = searchDao.findTopOrderByCount(5);
        if (searchList.size() == 0) {
            result.construct(false, "暂无搜索", searchList);
            return;
        }
        result.construct(true, "热搜", searchList);
    }
}
