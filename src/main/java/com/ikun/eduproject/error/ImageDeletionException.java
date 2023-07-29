package com.ikun.eduproject.error;

/**
 * @Author zzhay
 * @Date 2023/7/28/028
 * aliOSS删除异常
 */
public class ImageDeletionException extends RuntimeException {
    public ImageDeletionException(String message) {
        super(message);
    }

    public ImageDeletionException(String message, Throwable cause) {
        super(message, cause);
    }
}
