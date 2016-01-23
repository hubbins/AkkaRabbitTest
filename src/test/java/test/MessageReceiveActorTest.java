package test;

import actor.MessageReceiveActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MessageReceiveActorTest {

    ActorSystem system = ActorSystem.create();

    @Test
    public void itShouldSendAMessage() {
        TestActorRef<MessageReceiveActor> actorRef = TestActorRef.create(system, Props.create(MessageReceiveActor.class));
        actorRef.tell("**** TESTING ****", ActorRef.noSender());
    }
}