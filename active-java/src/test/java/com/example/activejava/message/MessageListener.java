package com.example.activejava.message;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;

import javax.jms.*;

/**
 * @author LinYongJin
 * @date 2019/9/5 21:49
 */
public class MessageListener {

    private static final String ACTIVE_URL = "tcp://192.168.0.110:61616";

    private static Logger logger = LoggerFactory.getLogger(MessageListener.class);

    public static void main(String[] args) throws JMSException, InterruptedException {
        logger.info("我是2号消费者");
        //普通的receive方法是同步阻塞的,messageListener方法是异步的,后面的程序依旧会在执行,监听器回去监听队列
        //当队列中有新消息的时候就会将新消息打印出来。
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
        //设置消息监听器
        consumer.setMessageListener(new javax.jms.MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("收到的消息是: " + textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        while (true) {
            System.out.println("程序还在执行哦");
            Thread.sleep(2000L);
        }
    }
}
