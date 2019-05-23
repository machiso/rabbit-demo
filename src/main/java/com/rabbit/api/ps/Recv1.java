package com.rabbit.api.ps;

import com.rabbit.api.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv1 {

    private static final String QUEUE_NAME="work_queue_email";
    private static final String EXCHANGE_NAME="channel_fanout";


    public static void main(String[] args) throws IOException, TimeoutException {
        final Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //绑定队列到交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");

        channel.basicQos(1);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("[Recv1]:"+msg);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[Recv1] done");
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };

        //自动确认关闭，，改成手动确认
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
}
}
