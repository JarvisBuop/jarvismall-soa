import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

/**
 * Created by JarvisDong on 2018/9/12.
 */
public class TestActiveMq {

    @Test
    public void testQueueProducer() throws Exception {
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.142.129:61616");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("test1");
//        Queue queue = session.createTopic("");
        MessageProducer producer = session.createProducer(queue);
        TextMessage textMessage = new ActiveMQTextMessage();
        textMessage.setText("llllllaaaa!!!!");
        producer.send(textMessage);
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testQueueConsumer() throws Exception {
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.142.129:61616");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("test1");

        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {

                if (message instanceof TextMessage) {
                    TextMessage txtMessage = (TextMessage) message;
                    try {
                        String text = txtMessage.getText();
                        System.out.print("resultmsg: " + text);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read();

        consumer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testTopicProducer() throws Exception {
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.142.129:61616");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        Queue queue = session.createQueue("test1");
        Topic topic = session.createTopic("test2");
        MessageProducer producer = session.createProducer(topic);
        TextMessage textMessage = new ActiveMQTextMessage();
        textMessage.setText("topictest!!!!");
        producer.send(textMessage);
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testTopicConsumer1() throws Exception {
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.142.129:61616");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("test2");
        MessageConsumer consumer = session.createConsumer(topic);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {

                if (message instanceof TextMessage) {
                    TextMessage txtMessage = (TextMessage) message;
                    try {
                        String text = txtMessage.getText();
                        System.out.print("\nresultmsg1: " + text + "\n");
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read();

        consumer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testTopicConsumer2() throws Exception {
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.142.129:61616");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("test2");
        MessageConsumer consumer = session.createConsumer(topic);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {

                if (message instanceof TextMessage) {
                    TextMessage txtMessage = (TextMessage) message;
                    try {
                        String text = txtMessage.getText();
                        System.out.print("\nresultmsg2: " + text + "\n");
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read();

        consumer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testSubscribeProducer() throws Exception {
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.142.129:61616");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("test3");

        MessageProducer producer = session.createProducer(topic);
        TextMessage textMessage = new ActiveMQTextMessage();
        textMessage.setText("subscribe test!!!!");
        producer.send(textMessage);
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testSubscribeConsumer() throws Exception {
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.142.129:61616");
        Connection connection = factory.createConnection();
        connection.setClientID("client01");
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("test3");

        TopicSubscriber consumer = session.createDurableSubscriber(topic, "sub_name");
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {

                if (message instanceof TextMessage) {
                    TextMessage txtMessage = (TextMessage) message;
                    try {
                        String text = txtMessage.getText();
                        System.out.print("\nresultmsg2: " + text + "\n");
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        System.in.read();

        consumer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testJmsTemplate() throws JMSException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        JmsTemplate template = (JmsTemplate) applicationContext.getBean("jmsTemplate");
        Destination destination = (Destination) applicationContext.getBean("activeMQQueue");

        template.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
//                TextMessage message = new ActiveMQTextMessage();
                TextMessage message = session.createTextMessage();
                message.setText("message--");
                return message;
            }
        });
    }

}
