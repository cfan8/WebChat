package com.linangran.webchat.imp

import com.google.gson.Gson
import com.linangran.webchat.base.Logger
import com.linangran.webchat.base.data.UserSession
import com.linangran.webchat.base.interfaces.ApplicationContextInterface
import com.linangran.webchat.imp.plugins.BasePlugin

/**
 * Created by linangran on 3/30/2015.
 */
class ApplicationContext implements ApplicationContextInterface {
    String configPath;
    public List<Extension> extensions;
    @Override
    UserSession getUserSession() {
        return new ChatSession();
    }

    @Override
    void setExtensionPath(String path) {
        configPath = path;
    }

    @Override
    void init() {
        loadExtensions()
    }

    public HashSet<String> usernames = new HashSet<String>();

    void loadExtensions()
    {
        Logger.log("Loading extensions.");
        Gson gson = new Gson();
        ExtensionList list = gson.fromJson(new InputStreamReader(new FileInputStream(configPath)), ExtensionList.class);
        this.extensions = list.extensions;
        this.extensions.each {n ->
            if (n.url == null) {
                n.plugin = Class.forName(n.className).newInstance()
            }
            else
            {
                GroovyClassLoader loader = new GroovyClassLoader();
                n.plugin = loader.parseClass(new File(configPath, "../" + n.url)).newInstance();
            }
        };
        Logger.log(this.extensions.size() + " extensions loaded.");
    }
}

class Extension
{
    String keyword;
    String pattern;
    String className;
    String url;
    BasePlugin plugin;
}

class ExtensionList
{
    List<Extension> extensions;
}


