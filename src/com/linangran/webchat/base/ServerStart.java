package com.linangran.webchat.base;

import com.google.gson.Gson;
import com.linangran.webchat.base.data.ApplicationConfig;
import com.linangran.webchat.base.data.ServerConfig;
import com.linangran.webchat.base.interfaces.*;

import java.io.*;

/**
 * Created by linangran on 3/30/2015.
 */
public class ServerStart
{

	public static void main(String[] args)
	{
		String resource = ServerStart.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		File file = new File(resource);
		File[] files = file.listFiles();
		File jsonFile = null;
		for (File configFile: files)
		{
			if (configFile.getName().equals("server.json"))
			{
				jsonFile = configFile;
				break;
			}
		}
		if (jsonFile == null)
		{
			Logger.err("Configuration file: server.json not found. Program will exit.");
		}
		else
		{
			Logger.log("Configuration file: server.json found and loaded.");
			Gson gson = new Gson();
			ServerConfig serverConfig = null;
			try
			{
				serverConfig = gson.fromJson(new InputStreamReader(new FileInputStream(jsonFile)), ServerConfig.class);
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			File applicationfile = new File(jsonFile, serverConfig.config);
			if (applicationfile.exists() == false)
			{
				Logger.err("Application configuration file: config.json not found. Program will exit.");
			}
			else
			{
				Logger.log("Application configuration file: config.json found and loaded.");
				ApplicationConfig applicationConfig = null;
				try
				{
					applicationConfig = gson.fromJson(new InputStreamReader(new FileInputStream(applicationfile)), ApplicationConfig.class);
				}
				catch (FileNotFoundException e)
				{
					e.printStackTrace();
				}
				Logger.log("Initialization Listeners.");
				ApplicationContextInterface applicationContext = null;
				try
				{
					applicationConfig.onConnectInterface = (onConnectInterface) Class.forName(applicationConfig.packagePrefix + "." + applicationConfig.onConnectClass).newInstance();
					applicationConfig.onDisconnectInterface = (onDisconnectInterface) Class.forName(applicationConfig.packagePrefix + "." + applicationConfig.onDisconnectClass).newInstance();
					applicationConfig.onReceiveInterface = (onReceiveInterface) Class.forName(applicationConfig.packagePrefix + "." + applicationConfig.onReceiveClass).newInstance();
					applicationContext = (ApplicationContextInterface) Class.forName(applicationConfig.packagePrefix + "." + applicationConfig.applicationContextClass).newInstance();
				}
				catch (Exception e)
				{
					Logger.err("Error Loading Listeners." + e.toString());
					return;
				}
				Logger.log("Listeners Initialized.");
				WebChatServer webChatServer = new WebChatServer(serverConfig.port, serverConfig.expire, applicationConfig, applicationContext);
				applicationConfig.configFilePath = applicationfile.getAbsolutePath();
				webChatServer.start();
				Logger.log("Server started.");
			}
		}
		return;
	}
}
