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

    private static final String ACTIVE_URL = "tcp://192.168.0.108:61616";

    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("第二个监听者开始监听");
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVE_URL);
        Connection connection = factory.createConnection();
        connection.setClientID("jdbcId");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("jdbc-topic");
        TopicSubscriber durableSubscriber = session.createDurableSubscriber(topic, "topic-jdbc-info");
        connection.start();
        durableSubscriber.setMessageListener(message -> {
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
        durableSubscriber.close();
        session.close();
        connection.close();
    }
}
