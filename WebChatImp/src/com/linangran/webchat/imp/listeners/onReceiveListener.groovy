package com.linangran.webchat.imp.listeners

import com.linangran.webchat.base.data.ApplicationConfig
import com.linangran.webchat.base.data.RequestResponse
import com.linangran.webchat.base.data.ResponseMessage
import com.linangran.webchat.base.data.UserSession
import com.linangran.webchat.base.interfaces.*
import com.linangran.webchat.imp.ApplicationContext
import com.linangran.webchat.imp.ChatSession
import com.linangran.webchat.imp.Extension;

/**
 * Created by linangran on 3/30/2015.
 */
class onReceiveListener extends BaseListener implements onReceiveInterface {

    @Override
    RequestResponse response(ApplicationConfig config, ApplicationContextInterface context, UserSession session) {
        ChatSession chatSession = session;
        ApplicationContext applicationContext = context;
        if (chatSession.isLoggedin == false)
        {
            def username = session.input.toString();
            if (applicationContext.usernames.contains(username) == false)
            {
                applicationContext.usernames.add(username);
                chatSession.username = username;
                chatSession.isLoggedin = true;
                return new RequestResponse("Login Success! Type anything to send, or type /quit to quit.\r\n", ResponseMessage.BROADCAST_TYPE_SENDER)
                        .add(config.loginWelcome + " There are " + applicationContext.usernames.size() + " users online.\r\n", ResponseMessage.BROADCAST_TYPE_SENDER)
                .add("User " + username + " joined the chat.\r\n", ResponseMessage.BROADCAST_TYPE_EVERYONE_EXCEPT_SENDER);
            }
            else
            {
                return new RequestResponse("Login Fail! Username occupied, please type again!\r\n", ResponseMessage.BROADCAST_TYPE_SENDER);
            }
        }
        else
        {
            String text = session.input.toString().trim();
            if (text.startsWith("/"))
            {
                if (text.equals("/quit"))
                {
                    applicationContext.usernames.remove(chatSession.username);
                    return new RequestResponse("You are logged out. Welcome again! \r\n", ResponseMessage.BROADCAST_TYPE_SHOULD_DISCONNECT);
                }
                else
                {
                    String msg;
                    for (Extension e: applicationContext.extensions)
                    {
                        if (text.startsWith("/" + e.keyword))
                        {
                            def m = text =~ ~e.pattern;
                            if (!m)
                            {
                                msg = "Your syntax for extension " + e.keyword + "is incorrect.";
                            }
                            else
                            {
                                def param = [];
                                for (int i = 1; i <= m.groupCount(); i++)
                                {
                                    param[i - 1] = m.group(i);
                                }
                                msg = e.plugin.onRequest(config, context, session, param);
                                break;
                            }
                        }
                    }
                    if (msg == null)
                    {
                        msg = "Unrecognized Command, Please type again!";
                    }
                    msg += "\r\n";
                    return new RequestResponse(msg, ResponseMessage.BROADCAST_TYPE_SENDER);
                }
            }
            else
            {
                return new RequestResponse(chatSession.username + " : " + text + "\r\n", ResponseMessage.BROADCAST_TYPE_EVERYONE_EXCEPT_SENDER);
            }
        }
    }
}
