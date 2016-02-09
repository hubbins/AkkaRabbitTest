package app;


import actor.MessageReceiveActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Main {
    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("akkaRabbit");
        ActorRef actorRef = actorSystem.actorOf(Props.create(MessageReceiveActor.class));
        QueueManager.bindActor(actorSystem, actorRef);
    }
}
