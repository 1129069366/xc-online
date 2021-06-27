package com.xuecheng.test.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: xc-online
 * @description
 * @author: Mr.Yang
 * @create: 2021-06-25 17:59
 **/
@Configuration
public class RabbitmqConfig {
    //队列
    public static final String QUEUE_EMAIL = "queue_inform_email";
    public static final String QUEUE_CMS = "queue_inform_cms";
    //交换机
    public static final String EXCHANGE_TOPIC_INFORM = "exchange_topic_inform";
    //routing key   交换机和队列绑定的时候指定的路由
    public static final String ROUTING_EMAIL = "inform.#.email";
    public static final String ROUTING_CMS = "inform.#.cms.#";

    //声明交换机
    @Bean(EXCHANGE_TOPIC_INFORM)
    public Exchange EXCHANGE_TOPIC_INFORM(){
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPIC_INFORM).durable(true).build();
    }


    //声明QUEUE_EMAIL队列
    @Bean(QUEUE_EMAIL)
    public Queue QUEUE_EMAIL(){
        return new Queue(QUEUE_EMAIL);
    }

    //声明QUEUE_CMS队列
    @Bean(QUEUE_CMS)
    public Queue QUEUE_CMS(){
        return new Queue(QUEUE_EMAIL);
    }

    //绑定QUEUE_EMAIL队列和交换机
    @Bean
    public Binding BINDING_QUUEUE_INFORM_EMAIL(@Qualifier(QUEUE_EMAIL)Queue queue,@Qualifier(EXCHANGE_TOPIC_INFORM)Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_EMAIL).noargs();
    }

    //绑定QUEUE_EMAIL队列和交换机
    @Bean
    public Binding BINDING_QUUEUE_INFORM_CMS(@Qualifier(QUEUE_CMS)Queue queue,@Qualifier(EXCHANGE_TOPIC_INFORM)Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_CMS).noargs();
    }







}
