package top.coderak.core.api;

import java.time.LocalDateTime;

public class ApiResult<T> {

    private boolean success;
    private String code;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public ApiResult() {
        this.timestamp = LocalDateTime.now();
    }

    public static <T> ApiResult<T> ok() {
        ApiResult<T> result = new ApiResult<T>();
        result.setSuccess(true);
        result.setCode("0");
        result.setMessage("success");
        return result;
    }

    public static <T> ApiResult<T> ok(T data) {
        ApiResult<T> result = new ApiResult<T>();
        result.setSuccess(true);
        result.setCode("0");
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    public static <T> ApiResult<T> fail(String message) {
        ApiResult<T> result = new ApiResult<T>();
        result.setSuccess(false);
        result.setCode("500");
        result.setMessage(message);
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
