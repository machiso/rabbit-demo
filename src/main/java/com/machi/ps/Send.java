package com.machi.ps;

import com.machi.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static  final String EXCHANGE_NAME = "channel_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        String msg = "publish-subscribe";

        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());

        System.out.println("[Send] : "+msg);

        channel.close();
        connection.close();

    }

}
