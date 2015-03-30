package com.linangran.webchat.base;

import com.linangran.webchat.base.data.ApplicationConfig;

import java.net.ServerSocket;

/**
 * Created by linangran on 3/30/2015.
 */
public class WebChatServer
{

	int port;
	ApplicationConfig applicationConfig;
	int expire;

	public WebChatServer(int port, int expire, ApplicationConfig applicationConfig)
	{
		this.port = port;
		this.expire = expire;
		this.applicationConfig = applicationConfig;
	}

	public void start()
	{

	}
}
