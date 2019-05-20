package com.machi.dlx;

import com.machi.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        String exchangeName = "test-dlx-exchange";
        String routeKey = "dlx.#";
        String queueName = "test-dlx-queue";
        channel.exchangeDeclare(exchangeName,"topic",true,false,null);
        Map<String,Object> arguments = new HashMap<String, Object>();
        arguments.put("x-dead-letter-exchange","dlx.exchange");
        //这个agruments属性，要设置到声明队列上
        channel.queueDeclare(queueName,true,false,false,arguments);
        channel.queueBind(queueName,exchangeName,routeKey);

        //要进行死信队列的声明
        channel.exchangeDeclare("dlx.exchange","topic",true,false,null);
        channel.queueDeclare("dlx.queue",true,false,false,null);
        channel.queueBind("dlx.queue","dlx.exchange","#");

        channel.basicConsume(queueName,true,new MyConsumer(channel));
    }
}
