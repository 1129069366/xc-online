package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @program: xc-online
 * @description
 * @author: Mr.Yang
 * @create: 2021-06-25 15:39
 **/
public class Consumer02_subscribe_email {
    //声明队列名字
    private static final String QUEUE_EMAIL = "queue_inform_email";
    private static final String EXCHANGE_FANOUT_INFORM = "exchange_fanout_inform";

    public static void main(String[] args) {

        //通过连接工厂和和mq建立连接
        ConnectionFactory connectionFactory  =new ConnectionFactory();
        connectionFactory.setHost("1.15.124.206");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");  //rabbitmq默认虚拟机名称为"/"，虚拟机相当于一个独立的mq服务器
        //建立新连接
        Connection connection = null;
        try {
            connection = connectionFactory.newConnection();
            //创建与Exchange的通道，每个连接可以创建多个通道，每个通道代表一个会话任务
            Channel channel = connection.createChannel();
            //声明队列
            //String queue,boolean durable, boolean exclusive, boolean autoDelete,Map<String, Object> arguments
            //参数明细
            //1.队列名称  队列名称
            //2.是否持久化  是否持久化  如果持久化,mq重启后队列还在
            //3.是否独占连接      是否独占连接  队列只允许在该连接中访问,如果连接关闭队列自动删除,如果将此输参数设置为true可用于临时队列创建
            //4.是否自动删除   队列不用则自动删除
            //5.参数 可以设置一个队列的扩展参数,比如：可以设置队列的存活时间
            channel.queueDeclare(QUEUE_EMAIL,true,false,false,null);

            //声明交换机
            channel.exchangeDeclare(EXCHANGE_FANOUT_INFORM,BuiltinExchangeType.FANOUT);

            //绑定队列和交换机
            channel.queueBind(QUEUE_EMAIL,EXCHANGE_FANOUT_INFORM,"");

            DefaultConsumer defaultConsumer = new DefaultConsumer(channel){

                /**
                 * 参数明细
                 * @param consumerTag   消费者标签，用来标识消费者的   在channel.basicConsume()去指定 可以不指定
                 * @param envelope    信封  用来获取消息包的内容，可从中获取消息id(可用于标识消息已接收)，消息routingkey，交换机，消息和重传标志 (收到消息失败后是否需要重新发送)
                 * @param properties  收到的消息属性
                 * @param body         消息内容   channel.basicPublish()方法发过来的props
                 * @throws IOException
                 */
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body,"utf-8");
                    System.out.println("receive message:"+message);
                }
            };
            //监听队列
            /**String queue, boolean autoAck, Map<String, Object> arguments, Consumer callback
             * 参数明细
             * 1.queue 队列名称
             * 2.autoAck 自动回复，当消费者接收到消息后要告诉mq消息已接收，如果将此参数设置为true会自动回复mq,如果设置为false则需要手动回复(编程实现)
             * 3.callback 消费方法  接收到消息之后要执行的逻辑
             */
            channel.basicConsume(QUEUE_EMAIL,true,defaultConsumer);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }

    }
}
