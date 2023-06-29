package com.lopez.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProductorSimple {

    public static void main(String[] args) throws IOException, TimeoutException {

        String message = "Hola mundo, from JAVA";

        // Abrir conexion AMQ y establecer canl
        ConnectionFactory connectionFactory = new ConnectionFactory();
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel();)
        {
            String queueName = "primera-cola";
            // Crear cola
            channel.queueDeclare(queueName, false, false, false, null);

            // Enviar mensaje al exchage ""
            channel.basicPublish("", queueName, null, message.getBytes());

        }
    }
}
