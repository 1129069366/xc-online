package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @program: xc-online
 * @description
 * @author: Mr.Yang
 * @create: 2021-06-25 16:32
 **/
public class Producer03_routing {
    //队列
    private static final String QUEUE_EMAIL = "queue_inform_email";
    private static final String QUEUE_CMS = "queue_inform_cms";
    //交换机
    private static final String EXCHANGE_ROUTING_INFORM = "exchange_routing_inform";
    //routing key
    private static final String ROUTING_EMAIL = "inform_queue_email";
    private static final String ROUTING_CMS = "inform_queue_cms";


    public static void main(String[] args) {

        //声明队列名字


        //通过连接工厂和和mq建立连接
        ConnectionFactory connectionFactory  =new ConnectionFactory();
        connectionFactory.setHost("1.15.124.206");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");  //rabbitmq默认虚拟机名称为"/"，虚拟机相当于一个独立的mq服务器
        //建立新连接
        Connection connection = null;
        Channel channel = null;
        try {
            connection = connectionFactory.newConnection();
            //创建与Exchange的通道，每个连接可以创建多个通道，每个通道代表一个会话任务
            channel = connection.createChannel();
            //声明队列
            //String queue,boolean durable, boolean exclusive, boolean autoDelete,Map<String, Object> arguments
            //参数明细
            //1.队列名称  队列名称
            //2.是否持久化  是否持久化  如果持久化,mq重启后队列还在
            //3.是否独占连接      是否独占连接  队列只允许在该连接中访问,如果连接关闭队列自动删除,如果将此输参数设置为true可用于临时队列创建
            //4.是否自动删除   队列不用则自动删除
            //5.参数 可以设置一个队列的扩展参数,比如：可以设置队列的存活时间
            channel.queueDeclare(QUEUE_EMAIL,true,false,false,null);
            channel.queueDeclare(QUEUE_CMS,true,false,false,null);
            /**
             * 参数明细
             * String exchange, String type
             * 1.交换机名称
             *
             * 2.交换机类型
             * fanout: 对应的rabbitmq的 发布订阅模式 publish/subscribe
             * direct: 对应的Routing模式   、Routing 路由模式
             * topic:  对应的路由模式   Topics 通配符模式
             * headers:                Header转发器
             */
            channel.exchangeDeclare(EXCHANGE_ROUTING_INFORM, BuiltinExchangeType.DIRECT);
            //交换机和队列绑定
            /**
             * String queue,String exchange, String routingKey
             * 1.queue 队列名称
             * 2.exchange 交换机名称
             * 3.routingKey 路由key  在fanout模式中为空串 ""
             */
            channel.queueBind(QUEUE_EMAIL,EXCHANGE_ROUTING_INFORM,ROUTING_EMAIL);
            channel.queueBind(QUEUE_EMAIL,EXCHANGE_ROUTING_INFORM,"email01");
            channel.queueBind(QUEUE_CMS,EXCHANGE_ROUTING_INFORM,"email01");
            channel.queueBind(QUEUE_CMS,EXCHANGE_ROUTING_INFORM,ROUTING_CMS);

//            //发送消息
//            for (int i = 0; i < 5; i++) {
//                //String exchange, String routingKey,  BasicProperties props, byte[] body
//                //参数明细
//                //1.exchange 交换机，如果不指定将使用默认交换机
//                //2.routingKey 消息的路由Key，是用于Exchange（交换机）将消息转发到指定的消息队列
//                //3.props 消息的属性
//                //4.body 消息的内容
//                String message = "send inform message to user";
//                //绑定交换机即可
//                channel.basicPublish(EXCHANGE_ROUTING_INFORM,ROUTING_EMAIL,null,message.getBytes());
//
//            }
//
//            for (int i = 0; i < 5; i++) {
//                //String exchange, String routingKey,  BasicProperties props, byte[] body
//                //参数明细
//                //1.exchange 交换机，如果不指定将使用默认交换机
//                //2.routingKey 消息的路由Key，是用于Exchange（交换机）将消息转发到指定的消息队列
//                //3.props 消息的属性
//                //4.body 消息的内容
//                String message = "send inform message to user";
//                //绑定交换机即可
//                channel.basicPublish(EXCHANGE_ROUTING_INFORM,ROUTING_CMS,null,message.getBytes());
//
//            }
            for (int i = 0; i < 5; i++) {
                //String exchange, String routingKey,  BasicProperties props, byte[] body
                //参数明细
                //1.exchange 交换机，如果不指定将使用默认交换机
                //2.routingKey 消息的路由Key，是用于Exchange（交换机）将消息转发到指定的消息队列
                //3.props 消息的属性
                //4.body 消息的内容
                String message = "send inform message to user";
                //绑定交换机即可
                channel.basicPublish(EXCHANGE_ROUTING_INFORM, "email01", null, message.getBytes());

            }


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
