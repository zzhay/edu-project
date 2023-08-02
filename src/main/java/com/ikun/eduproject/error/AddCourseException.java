package com.ikun.eduproject.error;

/**
 * @Author zzhay
 * @Date 2023/7/28/028
 * 课程新增异常
 */
public class AddCourseException extends RuntimeException {
    public AddCourseException(String message) {
        super(message);
    }

    public AddCourseException(String message, Throwable cause) {
        super(message, cause);
    }
}
