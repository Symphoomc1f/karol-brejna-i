package org.lantern.thrift.server.service.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.lantern.thrift.server.gen.UserService;
import org.lantern.thrift.support.EnableThriftServer;
import org.lantern.thrift.support.ThriftServerService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * UserServiceImpl
 * <p>
 * <p>
 * </p>
 *
 * @author Vigor Yuan
 */
@Service
@EnableThriftServer(genClass = UserService.class)
public class UserServiceImpl implements UserService.Iface, ThriftServerService {

    @Override
    public String getName() {
        return "userService";
    }

    @Override
    public TProcessor getProcessor(Object bean) {
        UserServiceImpl impl = (UserServiceImpl) bean;
        return new UserService.Processor<UserServiceImpl>(impl);
    }

    @Override
    public List<String> findAll() throws TException {
        return Arrays.asList(new String[] { "a", "b" });
    }
}
