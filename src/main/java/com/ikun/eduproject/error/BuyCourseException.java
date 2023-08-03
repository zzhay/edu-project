package com.ikun.eduproject.error;

/**
 * @Author zzhay
 * @Date 2023/7/28/028
 * 购买课程异常
 */
public class BuyCourseException extends RuntimeException {
    public BuyCourseException(String message) {
        super(message);
    }

    public BuyCourseException(String message, Throwable cause) {
        super(message, cause);
    }
}
