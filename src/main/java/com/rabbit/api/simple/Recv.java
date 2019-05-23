package com.rabbit.api.simple;

import com.rabbit.api.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv {

    private static final String QUEUE_NAME = "queue_simple";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            //获取到达的消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };

        //监听队列
        channel.basicConsume(QUEUE_NAME, true, consumer);

    }
}
