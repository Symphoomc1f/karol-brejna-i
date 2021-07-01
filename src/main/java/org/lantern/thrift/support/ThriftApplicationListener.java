package org.lantern.thrift.support;

import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TServerTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.*;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * ThriftApplicationListener
 * <p>
 * <p>
 * </p>
 *
 * @author Vigor Yuan
 */
public class ThriftApplicationListener implements ApplicationListener<ApplicationContextEvent> {

    Logger logger = LoggerFactory.getLogger(getClass());

    private ThriftServiceServerProxyBean proxyBeanLocal;

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {

        // 获取上下文
        ApplicationContext context = event.getApplicationContext();

        if (event instanceof ContextRefreshedEvent || event instanceof ContextStartedEvent) {
            initThriftServer(context);
            logger.debug("Thrift Server 配置加载成功");
        }

        if (event instanceof ContextStoppedEvent || event instanceof ContextClosedEvent) {
            try {
                proxyBeanLocal.destroy();
            } catch (Exception e) {

                logger.error("Thrift Server 关闭失败");
                e.printStackTrace();
            }
        }

    }

    private void initThriftServer(ApplicationContext context) {
        Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(EnableThriftServer.class);
        if (null == beansWithAnnotation || beansWithAnnotation.isEmpty()) {
            logger.warn("can not find annotation EnableThriftServer ");
            return;
        }

        TServerTransport tServerTransport = context.getBean(TServerTransport.class);
        if (null == tServerTransport) {
            logger.error("can not find bean with type TServerTransport");
            return;
        }

        TProtocolFactory tProtocolFactory = context.getBean(TProtocolFactory.class);
        if (null == tProtocolFactory) {
            logger.error("can not find bean with type tProtocolFactory");
            return;
        }

        Map<String, TProcessor> processorMap = new HashMap<String, TProcessor>();
        for (String beanName : beansWithAnnotation.keySet()) {
            logger.debug("find withAnnotation[EnableThriftServer] bean[{}] ", beanName);
            Object bean = context.getBean(beanName);

            ThriftServerService serverService = (ThriftServerService) bean;

            processorMap.put(serverService.getName() + "Processor", serverService.getProcessor(serverService));

        }

        final ThriftServiceServerProxyBean proxyBean = new ThriftServiceServerProxyBean(tServerTransport,
                tProtocolFactory, processorMap);

        proxyBeanLocal = proxyBean;
        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();

        simpleAsyncTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    proxyBean.afterPropertiesSet();
                } catch (Exception e) {
                    logger.error("Thrift Server 启动失败");
                    e.printStackTrace();
                }
            }
        });
    }

}
