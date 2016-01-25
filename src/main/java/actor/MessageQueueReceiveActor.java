package actor;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ExceptionHandler;
import com.typesafe.config.Config;
import rabbit.TestQueueConsumer;

public class MessageQueueReceiveActor extends AbstractActor {
    protected final LoggingAdapter log = Logging.getLogger(context().system(), this);
    final Config config = context().system().settings().config();
    final String queueName = this.config.getString("queue.name");
    private Channel channel;

    private MessageQueueReceiveActor() {
        receive(ReceiveBuilder.
                match(String.class, s -> {
                    log.info("Received String message: {}", s);
                }).
                matchAny(o -> log.info("received unknown message")).build()
        );
    }

    @Override
    public void preStart() {
        try {
            this.channel = this.createChannel();
            this.channel.queueDeclare(this.queueName, false, false, false, null);
            this.channel.basicConsume(this.queueName, true, new TestQueueConsumer(self(), this.channel));
            log.info("preStart() succeeded");
        } catch (Exception ex) {
            log.error(ex, "error in preStart()");
        }
    }

    @Override
    public void postStop() {
        try {
            Connection conn = this.channel.getConnection();
            this.channel.close();
            conn.close();
            log.info("postStop() succeeded");
        } catch (Exception ex) {
            log.error(ex, "error in postStop()");
        }
    }

    private Channel createChannel() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        Connection conn = factory.newConnection();
        return conn.createChannel();
    }
}
