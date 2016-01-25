package app;


import actor.MessageQueueReceiveActor;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Main {
    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("akkaRabbit");
        actorSystem.actorOf(Props.create(MessageQueueReceiveActor.class), "rabbitReceiver");
    }
}
