package com.linangran.webchat.base.interfaces;

import com.linangran.webchat.base.data.ApplicationConfig;
import com.linangran.webchat.base.data.RequestResponse;
import com.linangran.webchat.base.data.UserSession;

/**
 * Created by linangran on 3/30/2015.
 */
public interface onDisconnectInterface extends BaseInterface
{
	public RequestResponse response(ApplicationContextInterface context, ApplicationConfig config, UserSession session);
}
