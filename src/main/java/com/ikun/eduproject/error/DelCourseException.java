package com.ikun.eduproject.error;

/**
 * @Author zzhay
 * @Date 2023/7/28/028
 * 课程删除异常
 */
public class DelCourseException extends RuntimeException {
    public DelCourseException(String message) {
        super(message);
    }

    public DelCourseException(String message, Throwable cause) {
        super(message, cause);
    }
}
