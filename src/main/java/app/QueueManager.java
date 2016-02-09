package app;

import actor.MessageReceiveActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.typesafe.config.Config;
import rabbit.QueueConsumer;

public class QueueManager {

    public static void bindActor(ActorSystem actorSystem, ActorRef queueActor) {
        final Config config = actorSystem.settings().config();
        final String queueName = config.getString("queue.name");

        try {
            ConnectionFactory factory = new ConnectionFactory();
            Connection conn = factory.newConnection();
            Channel channel = conn.createChannel();

            channel.queueDeclare(queueName, false, false, false, null);
            channel.basicConsume(queueName, true, new QueueConsumer(queueActor, channel));
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
