
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.qpid.jms.JmsConnectionFactory;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author anh nguyen
 */
public class Consumer {

    public static void main(String[] args) throws JMSException {
        System.out.println("Create a ConnectionFactory");
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
//        ConnectionFactory connectionFactory = new JmsConnectionFactory("amqp://localhost:5672");

        System.out.println("Create a Connection");
        Connection connection = connectionFactory.createConnection("admin", "admin");
        connection.start();

        System.out.println("Create a Session");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        System.out.println("Create a Topic/ Queue");
        Destination destination = null;
        destination = session.createQueue("ws-jms-queue");
//        destination = session.createTopic("ws-jms-topic");

        System.out.println("Create a Consumer to receive messages from one Topic or Queue.");
        MessageConsumer consumer = session.createConsumer(destination);

        System.out.println("Start receiving messages … ");
        String body;
        do {
            Message msg = consumer.receive();
            body = ((TextMessage) msg).getText();
            System.out.println("Received = " + body);
        } while (!body.equalsIgnoreCase("close"));

        System.out.println("Shutdown JMS connection and free resources");
        connection.close();
        System.exit(1);
    }
}
