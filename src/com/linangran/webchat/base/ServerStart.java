package com.linangran.webchat.base;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.linangran.webchat.base.data.ApplicationConfig;
import com.linangran.webchat.base.data.ServerConfig;

import java.io.*;
import java.util.List;

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
				WebChatServer webChatServer = new WebChatServer(serverConfig.port, serverConfig.expire, applicationConfig);
				Logger.log("Server started.");
				webChatServer.start();
			}
		}
		return;
	}
}
