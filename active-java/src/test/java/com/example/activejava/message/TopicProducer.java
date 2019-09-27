package com.example.activejava.message;

import com.example.activejava.entries.News;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author LinYongJin
 * @date 2019/9/7 16:17
 */
public class TopicProducer {

    private static final String ACTIVE_URL = "tcp://192.168.0.108:61616";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVE_URL);
        Connection connection = factory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("jdbc-topic");
        MessageProducer producer = session.createProducer(topic);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        connection.start();
       for (int i = 1; i < 4; i++) {
           TextMessage textMessage = session.createTextMessage("订阅主题的内容" + i);
           producer.send(textMessage);
       }
        producer.close();
        session.close();
        connection.close();
    }
}
