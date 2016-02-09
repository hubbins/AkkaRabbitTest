package test;

import akka.actor.ActorSystem;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class QueueUtilTest {
    private QueueUtilTest() {}

    public static void sendMessage(String message, String queueName, ActorSystem actorSystem) {
        LoggingAdapter log = Logging.getLogger(actorSystem, "sendMessage");

        try {
            ConnectionFactory factory = new ConnectionFactory();
            Connection conn = factory.newConnection();
            Channel channel = conn.createChannel();

            channel.basicPublish("", queueName, null, message.getBytes());

            channel.close();
            conn.close();
        } catch (Exception ex) {
            log.error("Error sending message", ex);
        }
    }
}