package tv.moehub.model;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author wangrong
 * @date 2022/6/26 14:55
 */
@Data
public class BasePageResult<T> {
    private Boolean success;
    private String message;
    private List<T> data;
    private Integer totalPages;
    private Integer currentPage;
    private Long totalElement;

    public BasePageResult(Boolean success, String message, Page<T> page) {
        this.success = success;
        this.message = message;
        this.data = page.getContent();
        this.totalPages = page.getTotalPages();
        this.currentPage = page.getNumber() + 1;
        this.totalElement = page.getTotalElements();
    }

    public void construct(Boolean success, String message, Page<T> page) {
        this.success = success;
        this.message = message;
        this.data = page.getContent();
        this.totalPages = page.getTotalPages();
        this.currentPage = page.getNumber() + 1;
        this.totalElement = page.getTotalElements();
    }
}
