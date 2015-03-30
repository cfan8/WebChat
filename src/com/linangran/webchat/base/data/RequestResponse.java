package com.linangran.webchat.base.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linangran on 3/30/2015.
 */
public class RequestResponse
{
	public List<ResponseMessage> messages;

	public RequestResponse(String result, int broadcastType)
	{
		messages = new ArrayList<ResponseMessage>();
		messages.add(new ResponseMessage(result, broadcastType));
	}

	public RequestResponse add(String result, int broadcastType)
	{
		messages.add(new ResponseMessage(result, broadcastType));
		return this;
	}

	public RequestResponse()
	{
		messages = new ArrayList<ResponseMessage>();
	}
}
