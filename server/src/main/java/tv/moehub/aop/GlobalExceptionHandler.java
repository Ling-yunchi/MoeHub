package tv.moehub.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tv.moehub.model.BaseResult;

import java.util.List;


/**
 * @author wangrong
 * @date 2022/6/22 9:39
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class})
    public BaseResult<Void> exceptionHandle(Exception exception) {
        BindingResult result;
        if (exception instanceof BindException) {
            result = ((BindException) exception).getBindingResult();
        }else{
            result = ((MethodArgumentNotValidException) exception).getBindingResult();
        }
        StringBuilder errorMsg = new StringBuilder();

        List<FieldError> fieldErrors = result.getFieldErrors();
        fieldErrors.forEach(error -> {
            log.warn("field: " + error.getField() + ", msg:" + error.getDefaultMessage());
            errorMsg.append(error.getDefaultMessage()).append("!");
        });
        return new BaseResult<>(false, errorMsg.toString());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResult<Void> runtimeExceptionHandle(RuntimeException e) {
        e.printStackTrace();
        var realMessage = LogAspect.getRealMessage(e);
        return new BaseResult<>(false, "发生服务器错误，可能原因：" + realMessage);
    }
}
