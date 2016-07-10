package com.github.chm.exception;

/**
 * Created by chenhuaming on 16/7/10.
 */
public class DownloadImgException extends Exception {
    private static final long serialVersionUID = 1L;

    public DownloadImgException(String msg, Throwable e) {
        super(msg, e);
    }

    public DownloadImgException(String msg) {
        super(msg);
    }
}
