package com.machi.spring;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Send {
    public static void main(String[] args) throws InterruptedException {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("classpath:rabbitMQ.xml");
        RabbitTemplate template = context.getBean(RabbitTemplate.class);
        template.convertAndSend("Hello World");
        Thread.sleep(1000);
        context.destroy();
    }
}
