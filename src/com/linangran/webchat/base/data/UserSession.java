package com.linangran.webchat.base.data;

/**
 * Created by linangran on 3/30/2015.
 */
public abstract class UserSession
{
	public StringBuilder input = new StringBuilder();
	public abstract boolean shouldReceiveBroadcast();
}
