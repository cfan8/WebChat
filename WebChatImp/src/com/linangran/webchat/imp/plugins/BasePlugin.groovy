package com.linangran.webchat.imp.plugins

import com.linangran.webchat.base.data.ApplicationConfig
import com.linangran.webchat.base.data.UserSession
import com.linangran.webchat.base.interfaces.ApplicationContextInterface

/**
 * Created by linangran on 3/30/2015.
 */
abstract class  BasePlugin {

    public abstract String onRequest(ApplicationConfig config, ApplicationContextInterface context, UserSession session, def params);

    Map<String, CachedResponse> cache = new HashMap<>();

    def httpGet(def url)
    {
        if (cache[url] == null || System.currentTimeMillis() - cache[url].timestamp > 1000 * 3600 * 2)
        {
            cache[url] = new CachedResponse();
            cache[url].data = url.toURL().getText();
            cache[url].timestamp = System.currentTimeMillis();
        }
        return cache[url].data;
    }

    class CachedResponse
    {
        long timestamp;
        String data;
    }
}
