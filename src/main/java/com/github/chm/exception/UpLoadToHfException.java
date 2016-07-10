package com.github.chm.exception;

/**
 * Created by chenhuaming on 16/7/11.
 */
public class UpLoadToHfException extends Exception{
    private static final long serialVersionUID = 1L;

    public UpLoadToHfException(String msg, Throwable e) {
        super(msg, e);
    }

    public UpLoadToHfException(String msg) {
        super(msg);
    }
}
