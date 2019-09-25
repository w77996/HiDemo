package com.w77996.listener;

import com.w77996.es.IIndex;
import com.w77996.es.IRebuild;
import com.w77996.factory.ESFactory;
import com.w77996.index.IBatchRefresh;
import com.w77996.index.IndexContext;
import com.w77996.utils.ContextUtils;
import com.w77996.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class StartupListener implements ServletContextListener , ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger("SEARCH-" + StartupListener.class.getSimpleName());

    private static Timer task = new Timer();

    private static List<IndexContext> indexContextList;
    private ApplicationContext applicationContext;

    public static void refreshIndexName() {
        for (IndexContext indexContext : indexContextList) {
            try {
                indexContext.refreshIndexName();
                logger.info(indexContext.getIndex().getIndexName() + "刷新索引映射关系成功, 当前线上索引名：" + indexContext.getPhysicalOnlineIndexName() + " 当前重建索引名：" + indexContext.getPhysicalRebuildIndexName());
            } catch (Throwable t) {
                logger.error(indexContext.getIndex().getIndexName() + "刷新索引映射关系失败, 当前线上索引名：" + indexContext.getPhysicalOnlineIndexName() + " 当前重建索引名：" + indexContext.getPhysicalRebuildIndexName());
            }
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        long startTime = System.currentTimeMillis();
        try {
            Set<String> prefixSet = new HashSet<String>();
            prefixSet.add("basic");
//            MonitorHelper.addDataSourceMonitor(prefixSet);
//            MonitorHelper.addElasticsearchMonitor();
//            MonitorHelper.addRedisPersistentMonitor();
//            MonitorHelper.addKafkaConsumerMonitor();

            // 等待ES集群就绪
            ESFactory.getInstance().waitClusterNormal();
            // ESStatFactory.getInstance().waitClusterNormal();

            // kafka 消费端
            // MessageConsumer messageConsumer = new MessageConsumer();

            indexContextList = new ArrayList<IndexContext>();

            logger.info("----------------------  开始初始化各索引实例     ------------------------");
            List<IIndex> indexList = ContextUtils.getBeans(IIndex.class);
            for (IIndex index : indexList) {
//                if(StringUtils.equals(IDCUtil.getIdcName(), IDCUtil.IDC_NAME_BEIJING) && index.getIndexName().startsWith(IndexNameConst.ORDER_INDEX_PREFIX)) {
//                    logger.info("BEIJING IDC 不执行订单相关索引, indexName=" + index.getIndexName());
//                    continue;
//                }
                logger.info("  ----  初始化" + index.getIndexName() + "实例... ");
                // 构建索引实例上下文
                IndexContext indexContext = new IndexContext(index);
                // 索引实例初始化
                indexContext.init();

                indexContextList.add(indexContext);
                logger.info("  ----  " + indexContext.getIndex().getIndexName() + "索引实例进入可运行状态");
            }
            logger.info("----------------------  各索引实例初始化完毕     ------------------------");

            // kafka 消费端启动
            logger.info(" kafka 消费端初始化 ...");
//            messageConsumer.setIndexContextList(indexContextList);
//            messageConsumer.initToRun();
            logger.info("-------------------  kafka 消费端进入可运行状态     ----------------------");

            // 注册重建索引任务
            logger.info(" 重建索引 task 初始化 ...");

            registerRebuildIndexTask();

            registerRefreshIndexTask();

            logger.info("-------------------  重建索引任务注册完毕     ----------------------");

           // SearchServlet.setIndexContextList(indexContextList);
//            MonitorHelper.addMonitorItem(new MonitorItem("yytingsearchInit", State.success, "OK", (System.currentTimeMillis() - startTime)));
//            MonitorHelper.setRegisterMonitorFinish();
        } catch (Throwable e) {
            logger.error("Startup yytinghessianservlet failed.errorMsg=" + e.getMessage(), e);
            //MonitorHelper.addMonitorItem(new MonitorItem("yytingsearchInit", State.fail, "ERROR", (System.currentTimeMillis() - startTime)));
            throw new RuntimeException("Startup yytinghessianservlet failed.errorMsg=" + e.getMessage(), e);
        }
    }

    private void registerRefreshIndexTask() {
        Date runTime = getNextRefreshTime();
        task.schedule(new TimerTask() {
            @Override
            public void run() {

                // 刷新昨天的数据
                Date endTime = DateUtils.getStartTime();
                Date startTime = DateUtils.getYesterStartTime();
                batchRefresh(startTime, endTime);

                // 刷新一个月内的数据
                endTime = startTime;
                startTime = DateUtils.addDay(endTime, -29);
                batchRefresh(startTime, endTime);

                registerRefreshIndexTask();
            }

            private void batchRefresh(Date startTime, Date endTime) {
                long beginTime = System.currentTimeMillis();
                logger.info("开始索引刷新任务 ...");
                for (IndexContext indexContext : indexContextList) {
                    try {
//                        if (!indexContext.isMaster()) {
//                            logger.info(" ------  非主Search工程，不进行索引刷新     ------  ");
//                            continue;
//                        }
                        if (indexContext.getIndex() instanceof IBatchRefresh) {
                            IBatchRefresh batchRefresh = (IBatchRefresh) indexContext.getIndex();
                            batchRefresh.refresh(startTime, endTime);
                        } else {
                            logger.info("索引不支持刷新, indexName=" + indexContext.getIndex().getIndexName());
                        }
                    } catch (Throwable t) {
                        logger.error(indexContext.getIndex().getIndexName() + "索引刷新失败", t);
                    }
                }
                logger.info(" 索引刷新任务执行结束，startTime=" + "DateUtils.formatDateAndTime(startTime)" + " endTime=" + "DateUtils.formatDateAndTime(endTime)" + " 耗时：" + (System.currentTimeMillis() - beginTime) / 1000);
            }
        }, runTime);
    }

    private void registerRebuildIndexTask() {
        Date runTime = getNextTime(new Date());
        task.schedule(new TimerTask() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                logger.info(" 开始进行每天一次的索引重建任务 ...");
                for (IndexContext indexContext : indexContextList) {
                    try {
                        if (indexContext.getIndex() instanceof IRebuild) {
                            IRebuild rebuild = (IRebuild) indexContext.getIndex();
                            if (rebuild.doRebuildTask()) {
                                indexContext.getRebuildIndex().runRebulidJob();
                            } else {
                                logger.info("索引不进行重建, indexName=" + indexContext.getIndex().getIndexName());
                            }
                        } else {
                            logger.info("索引不支持重建, indexName=" + indexContext.getIndex().getIndexName());
                        }
                    } catch (Throwable t) {
                        logger.error(indexContext.getIndex().getIndexName() + "重建索引失败, 当前线上索引名：" + indexContext.getPhysicalOnlineIndexName() + " 当前重建索引名：" + indexContext.getPhysicalRebuildIndexName());
                    }
                }
                logger.info(" 索引重建任务执行结束，耗时：" + (System.currentTimeMillis() - startTime) / 1000);

                registerRebuildIndexTask();
            }
        }, runTime);
        logger.info("  -------  注册索引重建任务，预计允许日期为：" + "DateUtils.formatDateAndTime(runTime)" + "  -------  ");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    /**
     * 计算下一个切换索引时间点
     *
     * @return
     */
    private static Date getNextTime(Date startTime) {
        final int[] runDayOfWeeks = new int[]{2, 4, 6};
        final int runHour = 0;
        final int runMin = 30;

        Calendar c = Calendar.getInstance();
        c.setTime(startTime);
        int weekOfYear = c.get(Calendar.WEEK_OF_YEAR);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
        int minOfHour = c.get(Calendar.MINUTE);
        int chosseWeekOfYear = weekOfYear;
        int chosseDayOfWeek = -1;
        for (int runDayOfWeek : runDayOfWeeks) {
            if ((dayOfWeek < runDayOfWeek)
                    || (dayOfWeek == runDayOfWeek && hourOfDay == runHour && minOfHour < runMin)) {
                chosseDayOfWeek = runDayOfWeek;
                break;
            }
        }
        if (chosseDayOfWeek == -1) {
            chosseWeekOfYear = weekOfYear + 1;
            chosseDayOfWeek = runDayOfWeeks[0];
        }
        c.set(Calendar.WEEK_OF_YEAR, chosseWeekOfYear);
        c.set(Calendar.DAY_OF_WEEK, chosseDayOfWeek);
        c.set(Calendar.HOUR_OF_DAY, runHour);
        c.set(Calendar.MINUTE, runMin);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 计算下一个索引索引时间点
     *
     * @return
     */
    private static Date getNextRefreshTime() {
        Date runDate = new Date();
//        runDate = DateUtils.addDay(runDate, 1);
//        runDate = DateUtils.addHour(runDate, 1);
        return runDate;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
}
