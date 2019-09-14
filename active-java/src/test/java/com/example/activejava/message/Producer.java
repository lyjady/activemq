package com.example.activejava.message;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.aop.scope.ScopedObject;

import javax.jms.*;

/**
 * @author LinYongJin
 * @date 2019/9/5 21:27
 */
public class Producer {

    private static final String ACTIVE_URL = "tcp://localhost:61608";

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
        Queue queue = session.createQueue("windows-queue-nio");
        //创建消息生产者并设置目的地
        MessageProducer producer = session.createProducer(queue);
        //将消息发送到队列中
        for (int i = 1; i < 5; i++) {
            //创建消息
            MapMessage message = session.createMapMessage();
            message.setString("name", "Well" + i);
            //将消息发送到队列
            producer.send(message);
        }
        //关闭连接
        producer.close();
        session.close();
        connection.close();
    }
}
