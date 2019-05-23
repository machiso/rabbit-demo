package com.rabbit.api.work;

import com.rabbit.api.util.ConnectionUtils;
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
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

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
