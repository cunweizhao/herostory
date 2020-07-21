package com.zcw.herostory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * @ClassName : ServerMain
 * @Description : 服务器
 * @Author : Zhaocunwei
 * @Date: 2020-07-21 18:14
 */
public class ServerMain {
    public static void main(String[] args) {
        //创建两个线程池
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        //主要负责链接
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(boosGroup,workerGroup);
        b.channel(NioServerSocketChannel.class);


        //处理客户端请求
        b.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(
                        //解码
                        new HttpServerCodec(),
                        //限制他的长度
                        new HttpObjectAggregator(65535),
                        //约定输出的写法
                        new WebSocketServerProtocolHandler("/websocket"),
                        new GameMsgHandler()
                );
            }
        });

        try {
            ChannelFuture f = b.bind(12345).sync();
            if(f.isSuccess()){
                System.out.println("服务器启动成功");
            }

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
