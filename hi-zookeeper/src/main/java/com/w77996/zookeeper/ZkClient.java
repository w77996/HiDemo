package com.w77996.zookeeper;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Data
public class ZkClient {

    private CuratorFramework client;
    private ZookeeperProperties zookeeperProperties;

    public ZkClient(ZookeeperProperties zookeeperProperties){
        this.zookeeperProperties = zookeeperProperties;
    }
    /**
     * 初始化zookeeper客户端
     */
    public void init() {
        try{
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(zookeeperProperties.getBaseSleepTimeMs(),
                    zookeeperProperties.getMaxRetries());
            CuratorFrameworkFactory.Builder builder   = CuratorFrameworkFactory.builder()
                    .connectString(zookeeperProperties.getServer()).retryPolicy(retryPolicy)
                    .sessionTimeoutMs( zookeeperProperties.getSessionTimeoutMs())
                    .connectionTimeoutMs( zookeeperProperties.getConnectionTimeoutMs())
                    .namespace( zookeeperProperties.getNamespace());
            if(StringUtils.isNotEmpty( zookeeperProperties.getDigest())){
                builder.authorization("digest", zookeeperProperties.getDigest().getBytes("UTF-8"));
                builder.aclProvider(new ACLProvider() {
                    @Override
                    public List<ACL> getDefaultAcl() {
                        return ZooDefs.Ids.CREATOR_ALL_ACL;
                    }

                    @Override
                    public List<ACL> getAclForPath(final String path) {
                        return ZooDefs.Ids.CREATOR_ALL_ACL;
                    }
                });
            }
            client = builder.build();
            client.start();

            //   addConnectionStateListener();


            client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
                @Override
                public void stateChanged(CuratorFramework client, ConnectionState state) {
                    if (state == ConnectionState.LOST) {
                        //连接丢失
                        log.info("lost session with zookeeper");
                    } else if (state == ConnectionState.CONNECTED) {
                        //连接新建
                        log.info("connected with zookeeper");
                    } else if (state == ConnectionState.RECONNECTED) {
                        log.info("reconnected with zookeeper");
                    }
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }



    /**
     * 创建节点
     * @param mode       节点类型
     * 1、PERSISTENT 持久化目录节点，存储的数据不会丢失。
     * 2、PERSISTENT_SEQUENTIAL顺序自动编号的持久化目录节点，存储的数据不会丢失
     * 3、EPHEMERAL临时目录节点，一旦创建这个节点的客户端与服务器端口也就是session 超时，这种节点会被自动删除
     *4、EPHEMERAL_SEQUENTIAL临时自动编号节点，一旦创建这个节点的客户端与服务器端口也就是session 超时，这种节点会被自动删除，并且根据当前已近存在的节点数自动加 1，然后返回给客户端已经成功创建的目录节点名。
     * @param path  节点名称
     * @param nodeData  节点数据
     */
    public void createNode(CreateMode mode, String path , String nodeData) {
        try {
            //使用creatingParentContainersIfNeeded()之后Curator能够自动递归创建所有所需的父节点
            init();
            client.create().creatingParentsIfNeeded().withMode(mode).forPath(path,nodeData.getBytes("UTF-8"));
        } catch (Exception e) {
            log.error("注册出错", e);
        }
    }

    public void stop() {
        client.close();
    }

    public CuratorFramework getClient() {
        return client;
    }


}
