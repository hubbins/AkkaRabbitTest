package actor;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class MessageReceiveUntypedActor extends UntypedActor {
    protected final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            log.info("Received String message: {}", message);
            //getSender().tell(message, getSelf());
        } else
            unhandled(message);
    }
}
