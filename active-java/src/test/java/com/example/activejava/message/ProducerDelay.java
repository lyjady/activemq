package com.example.activejava.message;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;
import org.apache.activemq.ScheduledMessage;

import javax.jms.*;

/**
 * @author LinYongJin
 * @date 2019/9/24 21:50
 */
public class ProducerDelay {


    private static final String ACTIVE_URL = "tcp://192.168.0.110:61616";
    private static final String QUEUE_NAME = "delay-queue";
    private static final Long DELAY = 3000L;
    private static final Long PERIOD = 4000L;
    private static final Integer REPEAT = 5;

    public static void main(String[] args) throws JMSException {
        //创建连接工厂
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVE_URL);
        //通过工厂得到连接
        Connection connection = factory.createConnection();
        //开始连接
        connection.start();
        //创建回话对象(第一个参数是是否开启事务,第二参数是签收)
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建队列
        Queue queue = session.createQueue(QUEUE_NAME);
        //创建消息生产者并设置目的地
        ActiveMQMessageProducer producer = (ActiveMQMessageProducer) session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        //将消息发送到队列中
        for (int i = 1; i < 4; i++) {
            //创建消息
            TextMessage message = session.createTextMessage();
//            message.setJMSMessageID("jms" + i);
//            message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, DELAY);
//            message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, PERIOD);
//            message.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, REPEAT);
            message.setText("async-queue-message" + i);
            producer.send(message);

        }
        //关闭连接
        producer.close();
        session.close();
        connection.close();
    }
}
