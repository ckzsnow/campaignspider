package com.wisdom.common.queue;

import org.springframework.stereotype.Service;

@Service
public class DefaultMessageDelegate implements MessageDelegate {

	@Override
	public void handleMessage(String message) {
		// TODO Auto-generated method stub
		System.out.println(message + Thread.currentThread().getName());
		
	}

}
