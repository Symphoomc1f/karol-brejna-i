package org.lantern.thrift.server.service.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.lantern.thrift.server.gen.HelloService;
import org.lantern.thrift.support.EnableThriftServer;
import org.lantern.thrift.support.ThriftServerService;
import org.springframework.stereotype.Service;

/**
 * HelloServiceImpl
 * <p>
 * <p>
 * </p>
 *
 * @author Vigor Yuan
 */
@Service
@EnableThriftServer(genClass = HelloService.class)
public class HelloServiceImpl implements HelloService.Iface, ThriftServerService {

    @Override
    public String getName() {
        return "helloService";
    }

    @Override
    public TProcessor getProcessor(ThriftServerService bean) {
        HelloService.Iface impl = (HelloService.Iface) bean;
        return new HelloService.Processor<HelloService.Iface>(impl);
    }

    @Override
    public String sayHello() throws TException {
        return "Hello,World";
    }

    @Override
    public String sayName(String name) throws TException {
        return "hello," + name;
    }
}
