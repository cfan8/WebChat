package com.linangran.webchat.imp.plugins

import com.linangran.webchat.base.data.ApplicationConfig
import com.linangran.webchat.base.data.UserSession
import com.linangran.webchat.base.interfaces.ApplicationContextInterface
import groovy.json.JsonSlurper

/**
 * Created by linangran on 3/30/2015.
 */
public class WeatherPlugin extends BasePlugin {
    @Override
    String onRequest(ApplicationConfig config, ApplicationContextInterface context, UserSession session, def params)
    {
        def apidata = httpGet("http://api.openweathermap.org/data/2.5/weather?q=" + params[0]);
        def jsonSlurper = new JsonSlurper()
        def object = jsonSlurper.parseText(apidata);
        return object["weather"][0]["description"] + ", temperature is " + (object["main"]["temp"] - 273.15) + " degree.";

    }
}