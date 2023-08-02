package com.ikun.eduproject.error;

/**
 * @Author zzhay
 * @Date 2023/7/28/028
 * 课程状态修改异常
 */
public class ChangeCourseStatuException extends RuntimeException {
    public ChangeCourseStatuException(String message) {
        super(message);
    }

    public ChangeCourseStatuException(String message, Throwable cause) {
        super(message, cause);
    }
}
