package com.linangran.webchat.base.data;

/**
 * Created by linangran on 3/30/2015.
 */
public class ResponseMessage
{
	public String result;
	public int broadcastType;

	public static final int BROADCAST_TYPE_SENDER = 0;
	public static final int BROADCAST_TYPE_EVERYONE = 1;
	public static final int BROADCAST_TYPE_EVERYONE_EXCEPT_SENDER = 2;
	public static final int BROADCAST_TYPE_SHOULD_DISCONNECT = 3;
	public static final int BROADCAST_TYPE_DISCONNECT_NOW = 4;

	public ResponseMessage(String result, int broadcastType)
	{
		this.result = result;
		this.broadcastType = broadcastType;
	}
}
