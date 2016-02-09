package test;

import actor.MessageReceiveActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import app.QueueManager;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.typesafe.config.Config;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MessageReceiveActorTest {

    ActorSystem actorSystem = ActorSystem.create();
    final Config config = this.actorSystem.settings().config();
    final String queueName = this.config.getString("queue.name");

    @Test
    public void itShouldSendAMessage() {
        TestActorRef<MessageReceiveActor> actorRef = TestActorRef.create(actorSystem, Props.create(MessageReceiveActor.class));

        try {
            QueueManager.bindActor(actorSystem, actorRef);

            QueueUtilTest.sendMessage("Test message", queueName, actorSystem);

            actorRef.tell(PoisonPill.getInstance(), ActorRef.noSender());
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
