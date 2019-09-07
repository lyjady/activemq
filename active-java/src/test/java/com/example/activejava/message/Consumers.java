package com.example.activejava.message;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * @author LinYongJin
 * @date 2019/9/5 21:37
 */
public class Consumers {

    private static final String ACTIVE_URL = "tcp://192.168.0.110:61616";

    private static Logger logger = LoggerFactory.getLogger(Consumers.class);

    public static void main(String[] args) throws JMSException {
        logger.info("这是1号消费者");
        //创建连接工厂
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVE_URL);
        //通过工厂得到连接
        Connection connection = factory.createConnection();
        //开始连接
        connection.start();
        //创建回话对象(第一个参数是是否开启事务,第二参数是签收)
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //设置目的地队列
        Queue queue = session.createQueue("queue01");
        //设置消费者
        MessageConsumer consumer = session.createConsumer(queue);
        //接受消息
        while (true) {
            //无参的receive方法表示如果没有消息的话程序就会被阻塞住,直到收到消息为止。
            //指定的参数是超时时间,如果超过这个时间则断开连接继续执行下面的程序。
            MapMessage message = (MapMessage) consumer.receive();
            if (message != null) {
                String name = message.getString("name");
                System.out.println("收到的消息name: " + name);
                String property = message.getStringProperty("property");
                System.out.println("收到的消息属性property: " + property);
            } else {
                break;
            }
        }

        //关闭连接
        consumer.close();
        session.close();
        connection.close();
    }
}
