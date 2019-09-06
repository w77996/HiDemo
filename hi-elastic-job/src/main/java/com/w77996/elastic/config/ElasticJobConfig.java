package com.w77996.elastic.config;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.w77996.elastic.job.HiSimple2Job;
import com.w77996.elastic.job.HiSimpleJob;
import com.w77996.elastic.listener.HiElasticJobListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticJobConfig {

    @Autowired
    private ZookeeperRegistryCenter regCenter;

    /**
     * 配置任务监听器
     *
     * @return
     */
    @Bean
    public ElasticJobListener elasticJobListener() {
        return new HiElasticJobListener();
    }

    /**
     * 配置任务详细信息
     *
     * @param jobClass
     * @param cron
     * @param shardingTotalCount
     * @param shardingItemParameters
     * @return
     */
    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass,
                                                         final String cron,
                                                         final int shardingTotalCount,
                                                         final String shardingItemParameters) {
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount)
                .shardingItemParameters(shardingItemParameters).failover(true).misfire(false).build()
                , jobClass.getCanonicalName())
        ).overwrite(true).build();
    }

    /**
     * @param simpleJob              定时器
     * @param cron                   定时任务cron表达式
     * @param shardingTotalCount     分片总数
     * @param shardingItemParameters 分片策略
     * @return
     */
    @Bean(initMethod = "init")
    public JobScheduler simpleJobScheduler(final HiSimpleJob simpleJob, @Value("${stockJob.cron}") final String cron,
                                           @Value("${stockJob.shardingTotalCount}") final int shardingTotalCount,
                                           @Value("${stockJob.shardingItemParameters}") final String shardingItemParameters) {
        HiElasticJobListener elasticJobListener = new HiElasticJobListener();
        return new SpringJobScheduler(simpleJob, regCenter,
                getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters),
                elasticJobListener);
    }

    /**
     * @param simpleJob              定时器
     * @param cron                   定时任务cron表达式
     * @param shardingTotalCount     分片总数
     * @param shardingItemParameters 分片策略
     * @return
     */
    @Bean(initMethod = "init")
    public JobScheduler simple2JobScheduler(final HiSimple2Job simpleJob, @Value("${stockJob.cron}") final String cron,
                                            @Value("${stockJob.shardingTotalCount}") final int shardingTotalCount,
                                            @Value("${stockJob.shardingItemParameters}") final String shardingItemParameters) {
        HiElasticJobListener elasticJobListener = new HiElasticJobListener();
        return new SpringJobScheduler(simpleJob, regCenter,
                getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters),
                elasticJobListener);
    }
}
