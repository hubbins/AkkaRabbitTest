package test;

import actor.MessageReceiveActor;
import actor.MessageReceiveUntypedActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MessageReceiveUntypedActorTest {

    ActorSystem system = ActorSystem.create();

    @Test
    public void itShouldSendAMessageUntyped() {
        TestActorRef<MessageReceiveUntypedActor> actorRef = TestActorRef.create(system, Props.create(MessageReceiveUntypedActor.class));
        actorRef.tell("**** TESTING UNTYPED ****", ActorRef.noSender());
    }
}