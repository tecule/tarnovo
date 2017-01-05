package com.sinosoft.message;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public abstract class RetryMessageQueueClient {
	protected static final String WORK_EXCHANGE_NAME = "work_exchange";
	protected static final String WORK_QUEUE_NAME = "work_queue";
	protected static final String RETRY_EXCHANGE_NAME = "retry_exchange";
	protected static final String RETRY_QUEUE_NAME = "retry_queue";
	
	/*
	 * ttl in ms
	 */
	protected static final int RETRY_QUEUE_MESSAGE_TTL = 30000;
	protected static final int PER_MESSAGE_TTL = 60000;
	
	protected Connection connection;
	protected Channel channel;

	/**
	 * connect to message queue server and create queue.
	 * 
	 * @param host
	 *            - server name or ip
	 * @param virtualHost
	 *            - virtual host
	 * @param username
	 *            - message queue user name
	 * @param password
	 *            - password
	 * @throws IOException
	 * @throws TimeoutException
	 * @author xiangqian
	 */
	public void connect(String host, String virtualHost, String username, String password) throws IOException,
			TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);
		factory.setVirtualHost(virtualHost);
		factory.setUsername(username);
		factory.setPassword(password);
		/*
		 * connection is a socket abstraction
		 */
		connection = factory.newConnection();
		/*
		 * channel is API calling from
		 */
		channel = connection.createChannel();
		
		/*
		 * declare a direct exchange.
		 */
		channel.exchangeDeclare(WORK_EXCHANGE_NAME, "direct");
		
		/*
		 * set queue as durable, so the message will not lost after rabbitmq server dies.
		 */
		boolean durable = true;
		channel.queueDeclare(WORK_QUEUE_NAME, durable, false, false, null);	
		
		/*
		 * binding.
		 */
		channel.queueBind(WORK_QUEUE_NAME, WORK_EXCHANGE_NAME, "");	
	}

	/**
	 * close message queue connection.
	 * 
	 * @throws IOException
	 * @throws TimeoutException
	 * @author xiangqian
	 */
	public void close() throws IOException, TimeoutException {
		channel.close();
		connection.close();
	}
}
