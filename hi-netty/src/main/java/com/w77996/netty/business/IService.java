package com.w77996.netty.business;

import com.alibaba.fastjson.JSONObject;
import com.w77996.netty.dto.SocketBaseDto;
import io.netty.channel.Channel;


public interface IService {

	SocketBaseDto process(JSONObject jsonObject, Channel incoming);
	
}
