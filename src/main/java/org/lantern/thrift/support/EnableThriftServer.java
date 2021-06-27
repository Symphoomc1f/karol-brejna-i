package org.lantern.thrift.support;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * EnableThriftServer
 * <p>
 * <p>
 * </p>
 *
 * @author Vigor Yuan
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ThriftConfiguration.class)
@AutoConfigureAfter
public @interface EnableThriftServer {

    Class<?> genClass() ;
}
