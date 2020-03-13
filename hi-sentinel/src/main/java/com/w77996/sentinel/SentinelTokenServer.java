package com.w77996.sentinel;

import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterFlowRuleManager;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

public class SentinelTokenServer {
    /**
     * 应用名称api,web等
     */
    private static final String APP_NAME = "hi-sentinel";

    private static final String FLOW_POSTFIX = "-flow-rules";
    private static final String PARAM_FLOW_POSTFIX = "-param-rules";

    private static final int CLUSTER_SERVER_PORT = 11111;


    private static final String REMOTE_ADDRESS = "116.62.144.245";
    private static final String GROUP_ID = "SENTINEL_GROUP";

    /**
     * 初始化集群限流的Supplier
     * 这样如果后期集群限流的规则发生变更的话，系统可以自动感知到
     */
    private void initClusterFlowSupplier() {
        // 为集群流控注册一个Supplier，该Supplier会根据namespace动态创建数据源
        ClusterFlowRuleManager.setPropertySupplier(namespace -> {
            // 使用 Nacos 数据源作为配置中心，需要在 REMOTE_ADDRESS 上启动一个 Nacos 的服务
            ReadableDataSource<String, List<FlowRule>> ds = new NacosDataSource<>(REMOTE_ADDRESS, GROUP_ID,
                    namespace + FLOW_POSTFIX,
                    source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {}));
            return ds.getProperty();
        });
    }

}
