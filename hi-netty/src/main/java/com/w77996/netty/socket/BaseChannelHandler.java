package com.w77996.netty.socket;

import com.w77996.netty.business.IBusinessHandler;
import com.w77996.netty.util.ChannelMap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Sharable
@Slf4j
public class BaseChannelHandler extends SimpleChannelInboundHandler<String> {

	@Autowired
	private IBusinessHandler businessHandler;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		Channel incoming = ctx.channel();
		log.info("[" + incoming.remoteAddress() + "]发送信息:" + msg);
		businessHandler.process(msg, incoming);
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception { // (2)
		Channel incoming = ctx.channel();
		log.info("add connect:" + incoming.remoteAddress());
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception { // (3)
		Channel incoming = ctx.channel();
		log.info("remove connect:" + incoming.remoteAddress());
		ChannelMap.removeChannel(incoming);
	}

	/**
	 * 当从Channel中读数据时被调用
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		super.channelRead(ctx, msg);
	}

	/**
	 * 当Channel变成活跃状态时被调用；Channel是连接/绑定、就绪的
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		log.info("channelActive:" + incoming.remoteAddress() + "在线");
	}

	/**
	 * Channel未连接到远端
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
		Channel incoming = ctx.channel();
		log.info("channelInactive:" + incoming.remoteAddress() + "掉线");
		incoming.close();
		ctx.close();
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		super.channelReadComplete(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Channel incoming = ctx.channel();
		ctx.close();
		log.error("exceptioncaught," + incoming.remoteAddress(), cause);
	}
}
