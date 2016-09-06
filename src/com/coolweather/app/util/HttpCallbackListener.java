package com.coolweather.app.util;

public interface HttpCallbackListener {
	public void onFinish(String reponse);
	
	public void onError(Exception e);
}
