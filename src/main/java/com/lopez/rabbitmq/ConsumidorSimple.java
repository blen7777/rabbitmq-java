package com.lopez.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

public class ConsumidorSimple {

    public static void main(String[] args) throws IOException, TimeoutException {

        String queueName = "primera-cola";
        // Abrir conexion
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.newConnection();

        // Establecer canal
        Channel channel = connection.createChannel();

        // Declarar cola "primera-cola"
        channel.queueDeclare(queueName, false, false, false, null);

        // Crear subscripcion a la cola "primera-cola" usando el comando basic.consume
        channel.basicConsume(queueName,
                true,
                (consumerTag, message) -> {
                    String messageBody =  new String(message.getBody(), Charset.defaultCharset());

                    System.out.println("Message: " + messageBody);
                    System.out.println("Exchange: " + message.getEnvelope().getExchange());
                    System.out.println("Routing key: " + message.getEnvelope().getRoutingKey());
                    System.out.println("Delivery Tag: " + message.getEnvelope().getDeliveryTag());

                },
                consumerTag -> {

                    System.out.println("Consumidor: " + consumerTag + " Cancelado");

                });
    }
}
