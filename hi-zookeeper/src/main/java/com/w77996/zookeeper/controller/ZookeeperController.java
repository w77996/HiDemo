package com.w77996.zookeeper.controller;

import com.w77996.zookeeper.ZkClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags="zookeeper基本操作")
@RequestMapping("/zk")
@RestController
@Slf4j
public class ZookeeperController {

    @Autowired
    private ZkClient zkClient;


    /**
     * 创建节点
     * @param type
     * @param znode
     * @return
     */
    @ApiOperation(value = "创建节点",notes = "在命名空间下创建节点")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="type",value = "节点类型:<br> 0 持久化节点<br> 1 临时节点<br>  2 持久顺序节点<br> 3 临时顺序节点",
                    allowableValues = "0,1,2,3",defaultValue="3",paramType = "path",required = true,dataType = "Long"),
            @ApiImplicitParam(name ="znode",value = "节点名称",paramType = "path",required = true,dataType = "String"),
            @ApiImplicitParam(name ="nodeData",value = "节点数据",paramType = "body",dataType = "String")
    })
    @RequestMapping(value = "/create/{type}/{znode}",method= RequestMethod.POST)
    private String create(@PathVariable Integer type, @PathVariable String znode, @RequestBody String nodeData){
        znode = "/" + znode;
        try {
            zkClient.createNode(CreateMode.fromFlag(type),znode,nodeData);
        } catch (KeeperException e) {
            e.printStackTrace();
        }
        return znode;
    }

}
