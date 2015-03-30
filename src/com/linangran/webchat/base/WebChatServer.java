package com.linangran.webchat.base;

import com.linangran.webchat.base.data.ApplicationConfig;
import com.linangran.webchat.base.data.RequestResponse;
import com.linangran.webchat.base.data.ResponseMessage;
import com.linangran.webchat.base.data.UserSession;
import com.linangran.webchat.base.interfaces.ApplicationContextInterface;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by linangran on 3/30/2015.
 */
public class WebChatServer extends Thread
{
	int port;
	ApplicationConfig applicationConfig;
	ApplicationContextInterface applicationContext;
	int expire;
	ServerSocketChannel ssc;
	Selector selector;
	ByteBuffer receiveByteBuffer = ByteBuffer.allocate(10000);
	CharBuffer receiveCharBuffer = receiveByteBuffer.asCharBuffer();
	ByteBuffer sendByteBuffer = ByteBuffer.allocate(10000);
	CharBuffer sendCharBuffer = sendByteBuffer.asCharBuffer();
	HashMap<String, UserSession> sessions = new HashMap<String, UserSession>();

	public WebChatServer(int port, int expire, ApplicationConfig applicationConfig, ApplicationContextInterface applicationContext)
	{
		this.port = port;
		this.expire = expire;
		this.applicationConfig = applicationConfig;
		this.applicationContext = applicationContext;
	}

	@Override
	public void run()
	{
		try
		{
			ssc = ServerSocketChannel.open();
			ssc.bind(new InetSocketAddress(this.port));
			ssc.configureBlocking(false);
			selector = Selector.open();
			ssc.register(selector, SelectionKey.OP_ACCEPT);
			SelectionKey key;
			while (true)
			{
				while (ssc.isOpen())
				{
					selector.select();
					Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
					while (iterator.hasNext())
					{
						key = iterator.next();
						iterator.remove();
						if (key.isAcceptable())
						{
							accept(key);
						}
						if (key.isReadable())
						{
							handle(key);
						}
					}
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	void accept(SelectionKey key)
	{
		try
		{
			SocketChannel sc = ((ServerSocketChannel) key.channel()).accept();
			String address = getAddressString(sc);
			sc.configureBlocking(false);
			sc.register(selector, SelectionKey.OP_READ, address);
			sendString(sc, applicationConfig.greeting);
			Logger.log("accepted connection from: " + address);
			sessions.put(address, applicationContext.getUserSession());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	void sendString(SocketChannel sc, String data) throws IOException
	{
		sendByteBuffer.clear();
		sendByteBuffer.put(data.getBytes());
		sendByteBuffer.flip();
		sc.write(sendByteBuffer);
		sendByteBuffer.clear();
	}

	String getAddressString(SocketChannel sc)
	{
		return (new StringBuilder(sc.socket().getInetAddress().toString())).append(":").append(sc.socket().getPort()).toString();
	}

	void handle(SelectionKey key)
	{
		SocketChannel sc = (SocketChannel) key.channel();
		String address = getAddressString(sc);
		UserSession session = sessions.get(address);
		StringBuilder sb = session.input;
		receiveByteBuffer.clear();
		int read = 0;
		boolean isClosed = false;
		try
		{
			while (isClosed == false && (read = sc.read(receiveByteBuffer)) > 0)
			{
				receiveByteBuffer.flip();
				for (int i = 0; i < receiveByteBuffer.limit(); i++)
				{
					byte b = receiveByteBuffer.get(i);
					if (b == '\r')
					{
						continue;
					}
					if (b == 0x08 && sb.length() != 0)
					{
						sb.deleteCharAt(sb.length() - 1);
					}
					else if (b == '\n')
					{
						RequestResponse response = applicationConfig.onReceiveInterface.response(applicationConfig, applicationContext, session);
						for (int j = 0; j < response.messages.size(); j++)
						{
							ResponseMessage message = response.messages.get(j);
							switch (message.broadcastType)
							{
								case ResponseMessage.BROADCAST_TYPE_SENDER:
									sendString(sc, message.result);
									break;
								case ResponseMessage.BROADCAST_TYPE_EVERYONE_EXCEPT_SENDER:
									broadcast(sc, message.result);
									break;
								case ResponseMessage.BROADCAST_TYPE_EVERYONE:
									broadcast(null, message.result);
									break;
								case ResponseMessage.BROADCAST_TYPE_SHOULD_DISCONNECT:
									sendString(sc, message.result);
									sessions.remove(address);
									response.messages.addAll(applicationConfig.onDisconnectInterface.response(applicationContext, applicationConfig, session).messages);
									break;
								case ResponseMessage.BROADCAST_TYPE_DISCONNECT_NOW:
									sc.close();
									isClosed = true;
									break;
							}
						}
						sb.setLength(0);
					}
					else
					{
						sb.append((char) b);
					}
				}
				receiveByteBuffer.clear();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		Logger.log((sb.toString()));
	}


	void broadcast(SocketChannel except, String message) throws IOException
	{
		for (SelectionKey key : selector.keys())
		{
			if (key.isValid() && key.channel() instanceof SocketChannel)
			{
				SocketChannel sch = (SocketChannel) key.channel();
				if (sch.equals(except) == false && sessions.get(getAddressString(sch)).shouldReceiveBroadcast())
				{
					sendString(sch, message);
				}
			}
		}
	}
}
