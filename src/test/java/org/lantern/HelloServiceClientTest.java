package org.lantern;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.junit.Before;
import org.junit.Test;
import org.lantern.thrift.server.gen.HelloService;

/**
 * HelloServiceClientTest
 * <p>
 * <p>
 * </p>
 *
 * @author Vigor Yuan
 */
public class HelloServiceClientTest {

    HelloService.Client client;

    @Before
    public void init() {
        TTransport transport = new TSocket("localhost", 8090);
        try {
            transport.open();
        } catch (TTransportException e) {
            e.printStackTrace();
        }

        TProtocol protocol = new TJSONProtocol(transport);
         client = new HelloService.Client(protocol);
    }

    @Test
    public void testSayHello() {
        try {
            System.out.println(client.sayHello());
        } catch (TException e) {
            e.printStackTrace();
        }
    }



}
