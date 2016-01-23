package actor;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;

public class MessageReceiveActor extends AbstractActor {
    protected final LoggingAdapter log = Logging.getLogger(context().system(), this);

    private MessageReceiveActor() {

        receive(ReceiveBuilder.
                match(String.class, message -> {
                    log.info("Value: {}", message);
                }).
                matchAny(o -> log.info("Received unknown message {}", o)).build()
        );
    }

}
