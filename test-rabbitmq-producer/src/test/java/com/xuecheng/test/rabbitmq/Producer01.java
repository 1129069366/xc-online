package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**rabbitmq的入门程序
 * @program: xc-online
 * @description
 * @author: Mr.Yang
 * @create: 2021-06-25 13:22
 **/
public class Producer01 {
    private static final String QUEUE = "helloworld";

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
            channel.queueDeclare(QUEUE,true,false,false,null);
            //发送消息

            //String exchange, String routingKey,  BasicProperties props, byte[] body
            //参数明细
            //1.exchange 交换机，如果不指定将使用默认交换机
            //2.routingKey 消息的路由Key，是用于Exchange（交换机）将消息转发到指定的消息队列
            //3.props 消息的属性
            //4.body 消息的内容
            String message = "hello world 黑马程序员";
            channel.basicPublish("",QUEUE,null,message.getBytes());

            System.out.println("send to mq:"+message);

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
