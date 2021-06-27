package com.xuecheng.test.rabbitmq.mq;

import com.rabbitmq.client.*;
import com.xuecheng.test.rabbitmq.config.RabbitmqConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @program: xc-online
 * @description
 * @author: Mr.Yang
 * @create: 2021-06-25 18:42
 **/
@Component
public class ReceiveHandler {

    @RabbitListener(queues = {RabbitmqConfig.QUEUE_EMAIL})    //RabbitListener  添加了这个注解,spring应用启动时会监听这个方法
    public void receive_email(String msg, Message message,Channel channel){
        System.out.println("接收到的message是:"+msg);
    }




}
