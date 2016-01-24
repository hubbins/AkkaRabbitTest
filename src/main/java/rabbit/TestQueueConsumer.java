package rabbit;

import akka.actor.ActorRef;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class TestQueueConsumer extends DefaultConsumer {

    private final ActorRef destinationActor;

    public TestQueueConsumer(ActorRef destinationActor, Channel channel) {
        super(channel);
        this.destinationActor = destinationActor;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
        this.destinationActor.tell(new String(body), ActorRef.noSender());
    }
}
