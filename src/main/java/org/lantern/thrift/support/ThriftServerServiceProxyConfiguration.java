package org.lantern.thrift.support;

import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TServerTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

/**
 * ThriftServerServiceProxyConfiguration
 * <p>
 * <p>
 * </p>
 *
 * @author Vigor Yuan
 */
@Configuration
@Conditional(ThriftServerServiceProxyConfiguration.ThriftServerConfigCondition.class)
public class ThriftServerServiceProxyConfiguration
        implements ImportAware, BeanPostProcessor, BeanFactoryAware, ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private BeanFactory beanFactory;

    private ApplicationContext applicationContext;

    private Class<?> configType;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (this.configType.isAssignableFrom(bean.getClass()) && bean instanceof ThriftServerService) {
            logger.debug("{} init thriftServer", beanName);
            TServerTransport tServerTransport = this.beanFactory.getBean(TServerTransport.class);
            TProtocolFactory tProtocolFactory = this.beanFactory.getBean(TProtocolFactory.class);
            ThriftServerService serverService = (ThriftServerService) bean;

            // 将applicationContext转换为ConfigurableApplicationContext
            ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;

            // 获取bean工厂并转换为DefaultListableBeanFactory
            DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext
                    .getBeanFactory();

            ThriftServiceServerProxyBean proxyBean = new ThriftServiceServerProxyBean(serverService.getName(),
                    tServerTransport, tProtocolFactory, serverService.getProcessor(bean));
            // 注册bean
           // defaultListableBeanFactory.registerSingleton(serverService.getName() + "ProxyBean", proxyBean);
            defaultListableBeanFactory.initializeBean(proxyBean,serverService.getName() + "ProxyBean");
            defaultListableBeanFactory.registerDisposableBean(serverService.getName()+"ProxyBean",proxyBean);
        }

        return bean;
    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        this.configType = ClassUtils.resolveClassName(importMetadata.getClassName(), null);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected static class ThriftServerConfigCondition extends SpringBootCondition {

        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            String[] enablers = context.getBeanFactory().getBeanNamesForAnnotation(EnableThriftServer.class);
            for (String name : enablers) {
                if (context.getBeanFactory().isTypeMatch(name, ThriftServerService.class)) {
                    return ConditionOutcome.match("found @EnableThriftServer on a ThriftServerService");
                }
            }
            return ConditionOutcome.noMatch("found no @EnableThriftServer on a ThriftServerService");
        }
    }
}
