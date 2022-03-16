
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
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
public class Producer {

    public static void main(String[] args) throws Exception {
        System.out.println("create a ConnectionFactory");
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

        System.out.println("Create a Producer to send messages to one Topic or Queue.");
        MessageProducer producer = session.createProducer(destination);

        System.out.println("Start sending messages … ");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));) {
            String response;
            do {
                System.out.print("Enter message: ");
                response = br.readLine().trim();
                TextMessage msg = session.createTextMessage(response);
                producer.send(msg);
            } while (!response.equalsIgnoreCase("close"));
        }

        System.out.println("Shutdown JMS connection and free resources");
        connection.close();
        System.exit(1);
    }
}
