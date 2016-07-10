package com.github.chm.exception;

/**
 * Created by chenhuaming on 16/7/8.
 */
public class InitDataConnectionPoolException extends Exception{

	private static final long serialVersionUID = 1L;

	public InitDataConnectionPoolException(String msg,Throwable e){
        super(msg,e);
    }
    public InitDataConnectionPoolException(String msg){
        super(msg);
    }
}
