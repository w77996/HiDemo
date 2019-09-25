package com.w77996.protobuf.protobuf; /**
 * 
 */

import com.w77996.protobuf.bean.SnsUserExtBean;
import com.w77996.protobuf.bean.User;

import java.util.Date;

/**
 * @author zhangzk
 *
 */
public class ProtobufConvert {
	public static UserProtos.UserBean parseToPBUser(SnsUserExtBean src){
		UserProtos.UserBean.Builder tgt = UserProtos.UserBean.newBuilder();

		if(src.getId() != null) { tgt.setId(src.getId());}
		if(src.getUserId() != null) { tgt.setUserId(src.getUserId());}
		if(src.getAccountName() != null) { tgt.setAccountName(src.getAccountName());}
		if(src.getPhoneNum() != null) { tgt.setPhoneNum(src.getPhoneNum());}
		if(src.getEmail() != null) { tgt.setEmail(src.getEmail());}
		if(src.getPassword() != null) { tgt.setPassword(src.getPassword());}
		/*if(src.getBaseState() != null) { tgt.setBaseState(src.getBaseState());}*/
		if(src.getUuid() != null) { tgt.setUuid(src.getUuid());}
		if(src.getUuids() != null) { tgt.setUuids(src.getUuids());}
		/*if(src.getOpenId() != null) { tgt.setOpenId(src.getOpenId());}
		if(src.getType() != null) { tgt.setType(src.getType());}*/
		if(src.getAreaIds() != null) { tgt.setAreaIds(src.getAreaIds());}
		if(src.getSex() != null) { tgt.setSex(src.getSex());}
		if(src.getBirthday() != null) { tgt.setBirthday(src.getBirthday().getTime());}
		if(src.getBackCover() != null) { tgt.setBackCover(src.getBackCover());}
		if(src.getLikeTags() != null) { tgt.setLikeTags(src.getLikeTags());}
		if(src.getAblumnCount() != null) { tgt.setAblumnCount(src.getAblumnCount());}
		if(src.getFollowCount() != null) { tgt.setFollowCount(src.getFollowCount());}
		if(src.getRemark() != null) { tgt.setRemark(src.getRemark());}
		if(src.getShortRemark() != null) { tgt.setShortRemark(src.getShortRemark());}
		if(src.getCreateTime() != null) { tgt.setCreateTime(src.getCreateTime().getTime());}
		if(src.getUpdateTime() != null) { tgt.setUpdateTime(src.getUpdateTime().getTime());}
		if(src.getNickName() != null) { tgt.setNickName(src.getNickName());}
		if(src.getSource() != null) { tgt.setSource(src.getSource());}
		if(src.getCover() != null) { tgt.setCover(src.getCover());}
		if(src.getFeatures() != null) { tgt.setFeatures(src.getFeatures());}
		if(src.getFlag() != null) { tgt.setFlag(src.getFlag());}
		if(src.getFolderCount() != null) { tgt.setFolderCount(src.getFolderCount());}
		if(src.getCollectionFolderCount() != null) { tgt.setCollectionFolderCount(src.getCollectionFolderCount());}
		if(src.getCollectionReadCount() != null) { tgt.setCollectionReadCount(src.getCollectionReadCount());}
		if(src.getStatus() != null) { tgt.setStatus(src.getStatus());}
		return tgt.build();
	}

	public static SnsUserExtBean parseToUser(UserProtos.UserBean src){
		SnsUserExtBean tgt = new SnsUserExtBean();

		if(src.getId() != 0) { tgt.setId(src.getId());}
		if(src.getUserId() != 0) { tgt.setUserId(src.getUserId());}
		if(src.getAccountName() != null) { tgt.setAccountName(src.getAccountName());}
		if(src.getPhoneNum() != null) { tgt.setPhoneNum(src.getPhoneNum());}
		if(src.getEmail() != null) { tgt.setEmail(src.getEmail());}
		if(src.getPassword() != null) { tgt.setPassword(src.getPassword());}
		/*if(src.hasBaseState()) { tgt.setBaseState((byte)src.getBaseState());}*/
		if(src.getUuid() != 0) { tgt.setUuid(src.getUuid());}
		if(src.getUuids() != null) { tgt.setUuids(src.getUuids());}
		/*if(src.hasOpenId()) { tgt.setOpenId(src.getOpenId());}
		if(src.hasType()) { tgt.setType(src.getType());}*/
		if(src.getAreaIds() != null) { tgt.setAreaIds(src.getAreaIds());}
		if(src.getSex() != 0) { tgt.setSex((byte)src.getSex());}
		if(src.getBirthday() != 0) { tgt.setBirthday(new Date(src.getBirthday()));}
		if(src.getBackCover() != null) { tgt.setBackCover(src.getBackCover());}
		if(src.getLikeTags() != null) { tgt.setLikeTags(src.getLikeTags());}
		if(src.getAblumnCount() != 0) { tgt.setAblumnCount(src.getAblumnCount());}
		if(src.getFollowerCount() != 0) { tgt.setFollowCount(src.getFollowCount());}
		if(src.getRemark() != null) { tgt.setRemark(src.getRemark());}
		if(src.getShortRemark() != null) { tgt.setShortRemark(src.getShortRemark());}
		if(src.getCreateTime() != 0) { tgt.setCreateTime(new Date(src.getCreateTime()));}
		if(src.getUpdateTime() != 0) { tgt.setUpdateTime(new Date(src.getUpdateTime()));}
		if(src.getNickName() != null) { tgt.setNickName(src.getNickName());}
		if(src.getSource() != 0) { tgt.setSource(src.getSource());}
		if(src.getCover() != null) { tgt.setCover(src.getCover());}
		if(src.getFeatures() != null) { tgt.setFeatures(src.getFeatures());}
		if(src.getFlag() != 0) { tgt.setFlag(src.getFlag());}
		if(src.getFolderCount() != 0) { tgt.setFolderCount(src.getFolderCount());}
		if(src.getCollectionFolderCount() !=0) { tgt.setCollectionFolderCount(src.getCollectionFolderCount());}
		if(src.getCollectionReadCount() != 0) { tgt.setCollectionReadCount(src.getCollectionReadCount());}
		if(src.getStatus() != 0) { tgt.setStatus(src.getStatus());;}
		return tgt;
	}

}
