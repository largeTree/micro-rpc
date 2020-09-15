package com.qiuxs.mrpc.ex;

public class ExceptionUtils {
	
	public static RuntimeException unchecked(Throwable e) {
		return new RuntimeException(e);
	}
	
}
