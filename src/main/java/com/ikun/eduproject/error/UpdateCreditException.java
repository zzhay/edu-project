package com.ikun.eduproject.error;

/**
 * @Author zzhay
 * @Date 2023/7/28/028
 * 购买课程异常
 */
public class UpdateCreditException extends RuntimeException {
    public UpdateCreditException(String message) {
        super(message);
    }

    public UpdateCreditException(String message, Throwable cause) {
        super(message, cause);
    }
}
