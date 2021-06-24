package org.lantern.thrift.support;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.lang.instrument.IllegalClassFormatException;

/**
 * thrift 接口代理类,组装server实现
 *
 * <p>
 * </p>
 *
 * @author Vigor Yuan
 */
public class ThriftServiceServerProxyBean implements InitializingBean, DisposableBean {

    Logger logger = LoggerFactory.getLogger(getClass());

    private String serviceName;

    private TServerTransport tServerTransport;

    private TProtocolFactory tProtocolFactory;

    private TProcessor processor;

    private TThreadPoolServer server;

    public ThriftServiceServerProxyBean(String serviceName, TServerTransport tServerTransport,
            TProtocolFactory tProtocolFactory, TProcessor processor) {
        this.serviceName = serviceName;
        this.tServerTransport = tServerTransport;
        this.tProtocolFactory = tProtocolFactory;
        this.processor = processor;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (null == tServerTransport) {
            throw new IllegalClassFormatException("tServerTransport is null");
        }
        if (null == tProtocolFactory) {
            throw new IllegalClassFormatException("tProtocolFactory is null");
        }
        if (null == processor) {
            throw new IllegalClassFormatException("processor is null");
        }

        TThreadPoolServer.Args args = new TThreadPoolServer.Args(tServerTransport);
        args.processor(processor);
        args.protocolFactory(tProtocolFactory);
        server = new TThreadPoolServer(args);
        server.serve();
    }

    @Override
    public void destroy() throws Exception {
        server.stop();
        server = null;

        logger.debug("ServerThread {} stop", serviceName);
    }
}
