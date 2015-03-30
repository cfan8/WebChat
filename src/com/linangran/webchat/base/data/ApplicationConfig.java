package com.linangran.webchat.base.data;

import com.linangran.webchat.base.interfaces.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linangran on 3/30/2015.
 */
public class ApplicationConfig
{
	public String greeting;
	public String loginWelcome;
	public String packagePrefix;
	public String onConnectClass;
	public String onReceiveClass;
	public String onDisconnectClass;
	public String extensions;
	public String configFilePath;
	public String applicationContextClass;

	public onConnectInterface onConnectInterface;
	public onDisconnectInterface onDisconnectInterface;
	public onReceiveInterface onReceiveInterface;
}
