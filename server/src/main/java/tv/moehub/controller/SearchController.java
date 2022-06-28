package tv.moehub.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tv.moehub.entity.Search;
import tv.moehub.model.BaseResult;
import tv.moehub.service.SearchService;

import java.util.List;

/**
 * @author wangkunling
 * @date 2022/6/22 14:22
 */
@RestController
@RequestMapping("/search")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/updateSearchCount")
    public BaseResult<Void> updateSearchCount(@RequestParam String statement) {
        BaseResult<Void> result = new BaseResult<>();
        searchService.updateSearchCount(statement, result);
        return result;
    }

    @GetMapping("/showHot")
    public BaseResult<List<Search>> showHot() {
        BaseResult<List<Search>> result = new BaseResult<>();
        searchService.showHot(result);
        return result;
    }
}
