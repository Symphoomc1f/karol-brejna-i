package org.lantern.thrift.support;

import org.apache.thrift.TProcessor;

/**
 * ThriftServerService
 * <p>
 * <p>
 * </p>
 *
 * @author Vigor Yuan
 */
public interface ThriftServerService {

    String getName();

    TProcessor getProcessor(ThriftServerService bean);
}
