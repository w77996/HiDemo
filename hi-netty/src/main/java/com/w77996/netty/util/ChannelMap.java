package com.w77996.netty.util;

import com.w77996.netty.dto.SocketLoginDto;
import io.netty.channel.Channel;
import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;

import java.util.HashMap;
import java.util.Map;


public class ChannelMap {
	private ChannelMap() {
	}

	/**
	 * key:imei,value:SocketLoginDto
	 */
	private static Map<String, SocketLoginDto> channelMap2 = new HashMap<String, SocketLoginDto>();

	/**
	 * key:channel,value:SocketLoginDto
	 */
	private static Map<Channel, SocketLoginDto> channelMap3 = new HashMap<Channel, SocketLoginDto>();

	/**
	 * 
	 * add channelInfo
	 * 
	 * @param imei
	 * @param dto
	 * @return void
	 * @exception
	 */
	public static void addChannel(String imei, SocketLoginDto dto) {
		channelMap2.put(imei, dto);
	}

	public static void addChannel(Channel channel, SocketLoginDto dto) {
		channelMap3.put(channel, dto);
	}

	public static SocketLoginDto getChannel(Channel channel) {
		return channelMap3.get(channel);
	}

	public static void removeChannel(String imei) {
		SocketLoginDto dto = channelMap2.get(imei);
		if (dto != null) {
			channelMap3.remove(dto.getChannel());
		}
		channelMap2.remove(imei);
	}

	public static SocketLoginDto getChannel(String imei) {
		return channelMap2.get(imei);
	}

	public static void removeChannel(Channel channel) {
		SocketLoginDto dto = channelMap3.get(channel);
		if (dto != null) {
			channelMap2.remove(dto.getImei());
		}

		channelMap3.remove(channel);
	}

	public static void main(String[] args) {
		System.out.println(Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2)));
	}
}
