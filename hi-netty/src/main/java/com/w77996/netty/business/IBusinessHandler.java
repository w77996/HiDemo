package com.w77996.netty.business;

import io.netty.channel.Channel;

public interface IBusinessHandler {
	
	void process(String json, Channel incoming);
	
}
