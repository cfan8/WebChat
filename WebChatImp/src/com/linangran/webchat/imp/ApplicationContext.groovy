package com.linangran.webchat.imp

import com.linangran.webchat.base.data.UserSession
import com.linangran.webchat.base.interfaces.ApplicationContextInterface

/**
 * Created by linangran on 3/30/2015.
 */
class ApplicationContext implements ApplicationContextInterface {
    @Override
    UserSession getUserSession() {
        return new ChatSession();
    }

    public HashSet<String> usernames = new HashSet<String>();

    void loadPlugins()
    {

    }
}
