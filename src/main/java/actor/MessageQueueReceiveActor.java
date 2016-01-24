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
    private TestQueueConsumer testConsumer;
    private Connection conn;
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
            ConnectionFactory factory = new ConnectionFactory();
            this.conn = factory.newConnection();
            this.channel = conn.createChannel();

            this.channel.queueDeclare(config.getString("test.queueName"), false, false, false, null);
            this.testConsumer = new TestQueueConsumer(self(), this.channel);
            this.channel.basicConsume(config.getString("test.queueName"), true, this.testConsumer);

            log.info("preStart() succeeded");
        } catch (Exception ex) {
            log.error(ex, "error in preStart()");
        }
    }

    @Override
    public void postStop() {

        try {
            this.channel.close();
            this.conn.close();

            log.info("postStop() succeeded");
        } catch (Exception ex) {
            log.error(ex, "error in postStop()");
        }

    }
}
