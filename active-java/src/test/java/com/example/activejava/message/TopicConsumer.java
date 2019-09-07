package com.example.activejava.message;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQObjectMessage;

import javax.jms.*;
import java.io.IOException;

/**
 * @author LinYongJin
 * @date 2019/9/7 16:23
 */
public class TopicConsumer {

    private static final String ACTIVE_URL = "tcp://192.168.0.110:61616";

    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("第二个监听者开始监听");
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVE_URL);
        Connection connection = null;
        Session session = null;
        MessageConsumer consumer = null;

        connection = factory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        connection.start();
        Topic topic = session.createTopic("topic01");
        consumer = session.createConsumer(topic);
        consumer.setMessageListener(message -> {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(Thread.currentThread().getName() + ": " + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read();
        consumer.close();
        session.close();
        connection.close();
    }
}
