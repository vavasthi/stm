package com.sanjnan.server.sanjnan;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * Created by vinay on 2/3/16.
 */
@Configuration
public class SanjnanEmbeddedJettyCustomizer {

    @Bean
    EmbeddedServletContainerCustomizer containerCustomizer(
            @Value("${keystore.file}") String keystoreFile,
            @Value("${keystore.pass}") final String keystorePass,
            @Value("${server.port:8080}") final String port,
            @Value("${jetty.acceptqueuesize:5000}") final String acceptQueueSize,
            @Value("${jetty.threadPool.maxThreads:2000}") final String maxThreads,
            @Value("${jetty.threadPool.minThreads:8}") final String minThreads,
            @Value("${jetty.threadPool.idleTimeout:60000}") final String idleTimeout)
            throws Exception {

        // This is boiler plate code to setup https on embedded Tomcat
        // with Spring Boot:

        final String absoluteKeystoreFile = new File(keystoreFile)
                .getAbsolutePath();

        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                JettyEmbeddedServletContainerFactory tomcat
                        = (JettyEmbeddedServletContainerFactory) container;
                tomcat.addServerCustomizers(new JettyServerCustomizer() {
                    @Override
                    public void customize(Server server) {

                        // Tweak the connection pool used by Jetty to handle incoming HTTP connections
                        final QueuedThreadPool threadPool = server.getBean(QueuedThreadPool.class);
                        threadPool.setMaxThreads(Integer.valueOf(maxThreads));
                        threadPool.setMinThreads(Integer.valueOf(minThreads));
                        threadPool.setIdleTimeout(Integer.valueOf(idleTimeout));

                        for (Connector connector : server.getConnectors()) {
                            if (connector instanceof ServerConnector) {
                                ServerConnector serverConnector = (ServerConnector)connector;
                                serverConnector.setAcceptQueueSize(Integer.valueOf(acceptQueueSize));
                            }
                        }
                    }
                });
            }
        };
    }
}