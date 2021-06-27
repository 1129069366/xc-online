package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.xuecheng.test.rabbitmq.config.RabbitmqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @program: xc-online
 * @description
 * @author: Mr.Yang
 * @create: 2021-06-25 17:02
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class Producer05_springboot {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //使用rabbitTemplate发送消息
    @Test
    public void testSendEmail(){
        String message = "send email message to user";
        /**
         *
         */
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPIC_INFORM,"inform.email",message);

    }


}
