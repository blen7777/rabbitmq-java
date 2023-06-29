package com.lopez.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class ProductorEventosDeportivos {

    // Declaramos la variable del queueName
    private static final String EXCHANGE = "eventos-deportivos";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        // Abrir conexion AMQ y establecer canal
        try(Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel())
        {
            // Crear topic exchange "eventos"
            channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.TOPIC);
            // pasi: es, fr, usa
            List<String> countries = Arrays.asList("es", "fr", "usa");
            // Deporte: futbol, tenis, voleibol
            List<String>  sports = Arrays.asList("futbol", "tenis", "voleibol");
            // Tipo evento: envivo, noticia,
            List<String> eventTypes =  Arrays.asList("envivo", "noticia");
            // Enviamos mensaje al fanaut exchange "evento"
            int count = 1;
            while (true) {
                shuffle(countries, sports, eventTypes);
                String country = countries.get(0);
                String sport = sports.get(0);
                String eventType = eventTypes.get(0);
                // routing-key -> country.sport.eventType
                String routingKey = country + "." + sport + "." + eventType;

                String message = "Evento " + count;
                System.out.println("Produciendo mensaje ("+ country + "," + sport + "," + eventType + "): " + message);
                channel.basicPublish(EXCHANGE, routingKey, null, message.getBytes());
                Thread.sleep(1000);
                count++;
            }
        }

    }

    private static void shuffle(List<String> countries, List<String> sports, List<String> eventTypes) {
        Collections.shuffle(countries);
        Collections.shuffle(sports);
        Collections.shuffle(eventTypes);
    }
}
