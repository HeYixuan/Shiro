package org.springframe.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ConsumerMessageListener implements MessageListener {

	public void onMessage(Message message) {
		TextMessage textMsg = (TextMessage) message;   
        System.err.println("客户接收到一个纯文本消息。");   
        try {   
            System.out.println("消息内容是：" + textMsg.getText());   
        } catch (JMSException e) {   
            e.printStackTrace();   
        }

	}

}
