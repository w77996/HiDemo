package com.w77996.netty.business;

import io.netty.channel.Channel;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class BusinessHandler implements IBusinessHandler {

//	@Autowired
//	SocketBusinessFactory socketBusinessFactory;
//	@Autowired
//	IApilogService apilogService;

	public void process(String json, Channel incoming) {
		log.info("接受到的消息 "+json);
		incoming.writeAndFlush("可以6666" + "\r\n");
//		IService service = null;
//		int type = 0;
//		int a = 0;
//		String no = null;
//		SocketBaseDto dto = null;
//		logger.info(json);
//		long startTime = System.currentTimeMillis();
//		String serviceName = "";
//		String resp = "";
//		int rstatus = 0;
//		String rmsg = "";
//		String imei = "";
//		SocketLoginDto socketLoginDto = ChannelMap.getChannel(incoming);
//		if (socketLoginDto != null) {
//			imei = socketLoginDto.getImei();
//		}
//		try {
//			JSONObject jsonObject = (JSONObject) JSON.parse(json);
//			a = jsonObject.getIntValue("a");
//			type = jsonObject.getIntValue("type");
//			no = jsonObject.getString("no");
//			service = socketBusinessFactory.getService(type);
//			serviceName = service.getClass().getName();
//			dto = service.process(jsonObject, incoming);
//			dto.setTimestamp(dto.getTimestamp() / 1000);
//		} catch (Exception e) {
//			logger.error("process error:", e);
//			rstatus = 1;
//			rmsg = e.getMessage();
//			dto = new SocketBaseDto();
//			dto.setMessage(e.getMessage());
//			dto.setTimestamp(new Date().getTime() / 1000);
//			dto.setNo(no);
//			dto.setType(type);
//			dto.setStatus(RespCode.SYS_ERR.getCode());
//			dto.setMessage("System error");
//			if (e instanceof BizException) {
//				dto.setStatus(((BizException) e).getCode());
//				dto.setMessage(e.getMessage());
//			}
//			if (e instanceof JSONException) {
//				dto.setStatus(RespCode.ERR_PARAM.getCode());
//				dto.setMessage("Params error.Not json type");
//			}
//		}
//		long time = System.currentTimeMillis() - startTime;
//		String responseJson = JSON.toJSONString(dto);
//		if (a == 0) { // 如果a是1，表示应答，则无需返回结果
//			incoming.writeAndFlush(responseJson + "\r\n");
//		}
	}
}
