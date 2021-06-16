package org.lantern.thrift.server.config;

import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

/**
 * ThriftServiceConfig
 * <p>
 * <p>
 * </p>
 *
 * @author Vigor Yuan
 */
@Configuration
public class ThriftServerConfig {

    int port=8090;

    String ipAddress="127.0.0.1";

    @Bean
    public TServerTransport tServerTransport() throws TTransportException {
        return new TServerSocket(new InetSocketAddress(ipAddress, port));
    }

    @Bean
    public TProtocolFactory tProtocolFactory() {
        // or new TJSONProtocol.Factory(true); Write out the TField names as a
        // string instead of the default integer value
        return new TJSONProtocol.Factory();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
