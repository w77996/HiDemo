package com.w77996.netty.dto;

import io.netty.channel.Channel;
import lombok.Data;

/**
 * 手环登录后保存信息
 * 
 */
@Data
public class SocketLoginDto {
	private String no;
	private Long user_id;
	private String imei;
	private String token;
	private Channel channel;
}
