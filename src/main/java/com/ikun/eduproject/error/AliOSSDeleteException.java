package com.ikun.eduproject.error;

/**
 * @Author zzhay
 * @Date 2023/7/28/028
 * aliOSS删除异常
 */
public class AliOSSDeleteException extends RuntimeException {
    public AliOSSDeleteException(String message) {
        super(message);
    }

    public AliOSSDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
