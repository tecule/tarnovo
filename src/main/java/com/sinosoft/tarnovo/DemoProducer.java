package com.sinosoft.tarnovo;

import java.io.IOException;
import java.util.Date;

import com.rabbitmq.client.AlreadyClosedException;
import com.sinosoft.message.RetryMessageProducer;

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
			
			for (int i = 0; i < 100; i++) {		
				try {
					System.out.println(i);
					producer.publish(new Date().toString());
				} catch (AlreadyClosedException e) {
					e.printStackTrace();
				}
				
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			producer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("producer end");
    }
}
