package tsystems.javaschool.client;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.log4j.Log4j;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Log4j
@Singleton
public class Receiver {

    private static final String QUEUE_NAME = "tariffQueue";
    private Connection connection;
    private Channel channel;

    @EJB
    private TariffBean tariffBean;

    public void getMessage() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            log.info(" !!! Received '" + message + "'");
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                log.warn(e.getMessage());
                e.printStackTrace();
            }
            tariffBean.loadTariffList();
            log.info("TariffBean was changed");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }

    public void close() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }
}
