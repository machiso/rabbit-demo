package com.machi.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtils {
    public static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("140.143.240.163");
        factory.setPort(5672);
        factory.setVirtualHost("/vhost_mmr");
        factory.setUsername("machi");
        factory.setPassword("machi");

        Connection connection = factory.newConnection();

        return connection;
    }
}
