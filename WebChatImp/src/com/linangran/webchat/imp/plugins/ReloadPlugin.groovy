package com.linangran.webchat.imp.plugins

import com.linangran.webchat.base.data.ApplicationConfig
import com.linangran.webchat.base.data.UserSession
import com.linangran.webchat.base.interfaces.ApplicationContextInterface
import com.linangran.webchat.imp.ApplicationContext

/**
 * Created by linangran on 3/30/2015.
 */
class ReloadPlugin extends BasePlugin{
    @Override
    String onRequest(ApplicationConfig config, ApplicationContextInterface context, UserSession session,def Object params) {
        ApplicationContext applicationContext = context;
        applicationContext.loadExtensions();
        return applicationContext.extensions.size() + " extensions reloaded.";
    }
}
