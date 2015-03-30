package com.linangran.webchat.base.interfaces;

import com.linangran.webchat.base.data.ApplicationConfig;
import com.linangran.webchat.base.data.RequestResponse;
import com.linangran.webchat.base.data.UserSession;

/**
 * Created by linangran on 3/30/2015.
 */
public interface onReceiveInterface extends BaseInterface
{
	public RequestResponse response(ApplicationConfig config, ApplicationContextInterface context, UserSession session);
}
