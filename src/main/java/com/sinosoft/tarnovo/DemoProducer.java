package com.sinosoft.tarnovo;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.sinosoft.message.RetryMessageProducer;
import com.sinosoft.message.SimpleMessageProducer;


public class DemoProducer {
    public static void main( String[] args )
    {
    	String host = "192.168.101.161";
    	String virtualHost = "/";
    	String username = "openstack";
    	String password = "123456";
    	
//		SimpleMessageProducer producer = new SimpleMessageProducer();
    	RetryMessageProducer producer = new RetryMessageProducer();
		try {
			producer.connect(host, virtualHost, username, password);
			producer.publish("hello");
			
			producer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("producer end");
    }
}
