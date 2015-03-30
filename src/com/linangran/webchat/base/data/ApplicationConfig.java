package com.linangran.webchat.base.data;

import java.util.List;

/**
 * Created by linangran on 3/30/2015.
 */
public class ApplicationConfig
{
	String greeting;
	List<String> onLoginListeners;
	List<String> onLogoffListeners;
	List<String> queryConfigs;
	String configFilePath;
}
