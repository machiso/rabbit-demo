package com.machi.simple;

import com.machi.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {
    public static void main(String[] args) throws IOException, TimeoutException {
        final String QUEUE_NAME = "queue_simple";
        //创建连接
        Connection connection = ConnectionUtils.getConnection();
        //建立通道
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String msg = "hello simple queue";

        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

        channel.close();

        connection.close();

    }
}
