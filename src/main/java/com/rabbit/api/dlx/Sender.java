package com.rabbit.api.dlx;

import com.rabbit.api.util.ConnectionUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        String exchangeName = "test-dlx-exchange";
        String routeKey = "dlx.save";
        String msg = "Hello RabbitMQ DLX Message";

        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                .expiration("10000")
                .build();

        channel.basicPublish(exchangeName,routeKey,true,properties,msg.getBytes());
    }
}
