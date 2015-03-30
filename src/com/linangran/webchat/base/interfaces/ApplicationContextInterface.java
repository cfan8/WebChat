package com.linangran.webchat.base.interfaces;

import com.linangran.webchat.base.data.UserSession;

/**
 * Created by linangran on 3/30/2015.
 */
public interface ApplicationContextInterface
{
	public UserSession getUserSession();

	public void setExtensionPath(String path);
	public void init();


}
