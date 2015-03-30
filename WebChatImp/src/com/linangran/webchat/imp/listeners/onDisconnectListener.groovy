package com.linangran.webchat.imp.listeners

import com.linangran.webchat.base.data.ApplicationConfig
import com.linangran.webchat.base.data.RequestResponse
import com.linangran.webchat.base.data.ResponseMessage
import com.linangran.webchat.base.data.UserSession
import com.linangran.webchat.base.interfaces.*
import com.linangran.webchat.imp.ApplicationContext
import com.linangran.webchat.imp.ChatSession;
/**
 * Created by linangran on 3/30/2015.
 */
class onDisconnectListener implements onDisconnectInterface
{
    @Override
    RequestResponse response(ApplicationContextInterface context, ApplicationConfig config, UserSession session) {
        ApplicationContext applicationContext = context;
        ChatSession chatSession = session;
        if (chatSession.isLoggedin)
        {
            return new RequestResponse("User " + chatSession.username + " left the chat.\r\n", ResponseMessage.BROADCAST_TYPE_EVERYONE_EXCEPT_SENDER).add("", ResponseMessage.BROADCAST_TYPE_DISCONNECT_NOW);
        }
    }
}
