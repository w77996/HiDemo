package com.w77996.netty.dto;

import lombok.Data;

/**
 * 返回json的dto映射类
 * 
 */
@Data
public class SocketBaseDto {

	/**
	 * 应答类型
	 */
	private int a = 1;
	/**
	 * 【数字】数据类型
	 */
	private int type;

	/**
	 * 【字符串】消息号
	 */
	private String no;

	/**
	 * 【数字】状态码
	 */
	private int status;
	/**
	 * 【字符串】"错误信息说明"
	 */
	private String message;

	/**
	 * 【数字】时间戳
	 */
	private Long timestamp;

	/**
	 * 【json】返回结果
	 */
	private Object data;



}
