/**
 * @author Brin.Liu
 * @email liubilin@gmail.com
 * @create 2012-2-2
 *          <p>
 */
package com.w77996.protobuf.bean;


import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SnsUserExtBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Long userId;
	
	/**用户账号*/
	private String accountName;
	/**邮箱账号*/
	private String email;
	/**用户手机号码*/
	private String phoneNum;
	/**用户密码*/
	private String password;
	/** 冗余用户表的Nick */
	private String nickName;
	/**用户头像地址 数据库字段 */
	private String cover;
	/** 主播个人动态 个性签名 */
	private String remark;
	/** 主播精简介绍 */
	private String shortRemark;
	/**性别*/
	private Byte sex;
	/**生日*/
	private Date birthday;
	/**背景图片*/
	private String backCover;
	/** 用户喜欢的标签名称列表，以逗号分隔 */
	private String likeTags;
	/**2012/12/26 防止重复登陆 登陆唯一标记*/
	private Integer uuid;   //旧字段，存放一个uuid(旧的token判断时,仍可能用到该字段的值)
	private String uuids;	//新字段，json格式存放多个uuid,允许多个终端（车载、app、wap）登录
	/** 注册用户来源 1 APP 2 WEB */
	private Integer source;
	/**状态 1有效的 2.未知.10.邮箱待验证.*/
	/*private Byte baseState;*/
	/**地区ID 110120 11代表省,0120代表市*/
	private String areaIds;
	/** 用户的喜欢数 */
	@Deprecated
	private Integer likeCount;
	/** 用户的专辑数 */
	private Integer ablumnCount;
	/** 用户的粉丝数 */
	@Deprecated
	private Integer followerCount;
	/** 用户关注数 */
	private Integer followCount;
	/** 用户听单数 */
	private Integer folderCount;
	/** 用户收藏听单数 */
	private Integer collectionFolderCount;
	/**用户收藏阅读书籍数 version rd1.1.0*/
	private Integer collectionReadCount;
	
	/** 复合字段，格式k1=v1;k2=v2,key/value参照com.lazyaudio.yyting.commons.constants.UserExtConst*/
	private String features;
	
	/**
	 * 用户标记，用位来表示用户身份
		第0位：1存在密保
		第1位：2邮箱通过验证
		第2位：4充值会员
		第3位：8认证主播
		第4位：16绑定新浪微博
		第5位：32绑定QQ
		第6位：64绑定豆瓣
		第7位：128绑定微信
		第8为：256书城主播
	 */
	private Long flag;
	
	/** 创建时间 */
	private Date createTime;
	/** 修改时间 */
	private Date updateTime;

	/**处理4字节及以上的utf8,使写入DB时不报错*/
	private transient String encodeNickName;
	
	/** 状态 */
	private Integer status;
	/** imei的首次使用时间*/
	private Date imeiRegTime;
	/**imei首次使用时被动注册用户ID*/
	private Long imeiRegUserId;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLikeTags() {
		return likeTags;
	}

	public void setLikeTags(String likeTags) {
		this.likeTags = likeTags;
	}

	@Deprecated
	public Integer getLikeCount() {
		return likeCount;
	}

	@Deprecated
	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public Integer getAblumnCount() {
		return ablumnCount;
	}

	public void setAblumnCount(Integer ablumnCount) {
		this.ablumnCount = ablumnCount;
	}

	@Deprecated
	public Integer getFollowerCount() {
		return followerCount;
	}

	@Deprecated
	public void setFollowerCount(Integer followerCount) {
		this.followerCount = followerCount;
	}

	public Integer getFollowCount() {
		return followCount;
	}

	public void setFollowCount(Integer followCount) {
		this.followCount = followCount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark==null?"":remark;
	}

	public String getShortRemark() {
		return shortRemark;
	}

	public void setShortRemark(String shortRemark) {
		this.shortRemark = shortRemark==null?"":shortRemark;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getUuid() {
		return uuid;
	}

	public void setUuid(Integer uuid) {
		this.uuid = uuid;
	}

	public String getUuids() {
		return uuids;
	}

	public void setUuids(String uuids) {
		this.uuids = uuids;
	}
	

	
	public Integer getClientUuid(){
		return uuid;
	}

	public void setClientUuid(Integer uuid){
		this. uuid = uuid.intValue();
	}
	
	public Byte getSex() {
		return sex;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getBackCover() {
		return backCover;
	}

	public void setBackCover(String backCover) {
		this.backCover = backCover;
	}
	
	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}	
		
	public Long getFlag() {
		return flag;
	}

	public void setFlag(Long flag) {
		this.flag = flag;
	}
	
	/**
	 * 增加标识位
	 */
	public long addFlag(){

		return this.flag;
	}
	



	
	public String getFeature(String key){
		return key;
	}

	
	public String getAreaIds() {
		return areaIds;
	}

	public void setAreaIds(String areaIds) {
		this.areaIds = areaIds;
	}

	public Integer getFolderCount() {
		return folderCount;
	}

	public void setFolderCount(Integer folderCount) {
		this.folderCount = folderCount;
	}

	public Integer getCollectionFolderCount() {
		return collectionFolderCount;
	}

	public void setCollectionFolderCount(Integer collectionFolderCount) {
		this.collectionFolderCount = collectionFolderCount;
	}

	public Integer getCollectionReadCount() {
		return collectionReadCount;
	}

	public void setCollectionReadCount(Integer collectionReadCount) {
		this.collectionReadCount = collectionReadCount;
	}

	public String getEncodeNickName() {
		return encodeNickName;
	}

	public void setEncodeNickName(String encodeNickName) {
		this.encodeNickName = encodeNickName;
		this.nickName =encodeNickName;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getImeiRegTime() {
		return imeiRegTime;
	}

	public void setImeiRegTime(Date imeiRegTime) {
		this.imeiRegTime = imeiRegTime;
	}

	public Long getImeiRegUserId() {
		return imeiRegUserId;
	}

	public void setImeiRegUserId(Long imeiRegUserId) {
		this.imeiRegUserId = imeiRegUserId;
	}

	@Override
	public String toString() {
		return "SnsUserExtBean{" + "id=" + id + ", userId=" + userId + ", accountName='" + accountName + '\''
				+ ", email='" + email + '\'' + ", phoneNum='" + phoneNum + '\'' + ", password='" + password + '\''
				+ ", nickName='" + nickName + '\'' + ", cover='" + cover + '\'' + ", remark='" + remark + '\''
				+ ", shortRemark='" + shortRemark + '\'' + ", sex=" + sex + ", birthday=" + birthday + ", backCover='"
				+ backCover + '\'' + ", likeTags='" + likeTags + '\'' + ", uuid=" + uuid + ", uuids:" + uuids + ", source=" + source
				+ ", areaIds='" + areaIds + '\'' + ", likeCount=" + likeCount + ", ablumnCount=" + ablumnCount
				+ ", followerCount=" + followerCount + ", followCount=" + followCount + ", folderCount=" + folderCount
				+ ", collectionFolderCount=" + collectionFolderCount + ", collectionReadCount=" + collectionReadCount
				+ ", features='" + features + '\'' + ", flag=" + flag + ", createTime=" + createTime + ", updateTime="
				+ updateTime + ", encodeNickName='" + encodeNickName + '\'' + '}';
	}
}
