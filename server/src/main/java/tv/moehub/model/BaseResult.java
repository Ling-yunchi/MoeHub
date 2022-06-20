package tv.moehub.model;

import lombok.Data;

/**
 * @author wangrong
 * @date 2022/6/20 15:27
 */
@Data
public class BaseResult<T> {
    private Boolean success;
    private String message;
    private T data;

    public BaseResult() {
    }

    public BaseResult(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public BaseResult(Boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public void construct(Boolean success, String message){
        this.success = success;
        this.message = message;
    }

    public void construct(Boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
