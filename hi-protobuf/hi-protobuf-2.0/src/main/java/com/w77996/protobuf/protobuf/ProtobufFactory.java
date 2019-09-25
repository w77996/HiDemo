package com.w77996.protobuf.protobuf; /**
 * 
 */

import com.w77996.protobuf.bean.SnsUserExtBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author zhangzk
 * 
 */
public class ProtobufFactory {
	private static Log logger = LogFactory.getLog(ProtobufFactory.class);

	public static void main(String[] args){

	}
	
	public static <T> byte[] toByteArray(T o) {
		// 为空则返回空对象
		if (o == null) {
			return null;
		}


		byte[] result = null;
		try {
			if( o instanceof SnsUserExtBean){
				result = ProtobufConvert.parseToPBUser((SnsUserExtBean)o).toByteArray();
			}else{
				throw new RuntimeException("Protobuf convert failed,type not supported." + o.getClass());
			}
		} catch (Exception e) {
			logger.error("[KryoError]errorMsg=" + e.getMessage(), e);
		}
		return result;
	}

	public static SnsUserExtBean toObject4UserBean(byte[] serializerBytes) {
		if (serializerBytes == null || serializerBytes.length == 0) {
			return null;
		}

		SnsUserExtBean result = null;
		try {
			return ProtobufConvert.parseToUser(UserProtos.UserBean.parseFrom(serializerBytes));
		} catch (Exception e) {
			logger.error("[KryoError]errorMsg=" + e.getMessage(), e);
		}
		return result;
	}
}
