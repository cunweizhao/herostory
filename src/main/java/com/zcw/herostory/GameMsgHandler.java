package com.zcw.herostory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

/**
 * @ClassName : GameMsgHandler
 * @Description : 处理客户端消息
 * @Author : Zhaocunwei
 * @Date: 2020-07-21 18:51
 */
public class GameMsgHandler extends SimpleChannelInboundHandler<Object> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        System.out.println("收到客户端消息， msg="+msg);

        BinaryWebSocketFrame frame = (BinaryWebSocketFrame)msg;
        ByteBuf byteBuf = frame.content();


        byte[] byteArray = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(byteArray);

        for(byte b:byteArray){
            System.out.println(b);
        }
    }
}
