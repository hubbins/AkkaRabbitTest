package test;

import actor.MessageQueueReceiveActor;
import actor.MessageReceiveActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.testkit.TestActorRef;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;

public class MessageQueueReceiveActorTest {

    ActorSystem actorSystem = ActorSystem.create();

    //@Test
    public void itShouldSendAMessage() {
        TestActorRef<MessageQueueReceiveActor> actorRef = TestActorRef.create(this.actorSystem, Props.create(MessageQueueReceiveActor.class));
        actorRef.tell("**** TESTING QUEUE RECEIVE ****", ActorRef.noSender());
    }

    @Test
    public void sendMessage() {
        TestActorRef<MessageQueueReceiveActor> actorRef = TestActorRef.create(this.actorSystem, Props.create(MessageQueueReceiveActor.class));

        try {
            ConnectionFactory factory = new ConnectionFactory();
            Connection conn = factory.newConnection();
            Channel channel = conn.createChannel();

            //channel.queueDeclare("test_queue", false, false, false, null);
            channel.basicPublish("", "test_queue", null, "test message 123".getBytes());

            channel.close();
            conn.close();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
