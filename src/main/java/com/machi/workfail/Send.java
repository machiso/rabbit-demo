package com.machi.workfail;

import com.machi.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final String QUEUE_NAME="work_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        //生明队列
        boolean durable = true;
        channel.queueDeclare(QUEUE_NAME,durable,false,false,null);

        int prefetchCount = 1;
        //每个消费者发送确认信号之前，消息队列不发送下一个消息
        //限制发给消费者每次一条消息
        channel.basicQos(prefetchCount);

        for (int i=1;i<=50;i++){
            String msg = "work-"+i;
            System.out.println("[Send] msg:"+msg);
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            Thread.sleep(i*20);
        }

        channel.close();
        connection.close();

    }

}
