package com.ikun.eduproject.error;

/**
 * @Author zzhay
 * @Date 2023/7/28/028
 * 课程更新异常
 */
public class UpdateCourseException extends RuntimeException {
    public UpdateCourseException(String message) {
        super(message);
    }

    public UpdateCourseException(String message, Throwable cause) {
        super(message, cause);
    }
}
