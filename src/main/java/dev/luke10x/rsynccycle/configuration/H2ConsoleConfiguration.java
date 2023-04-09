package dev.luke10x.rsynccycle.configuration;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import lombok.extern.apachecommons.CommonsLog;
import org.h2.server.web.ConnectionInfo;
import org.h2.server.web.WebServer;

import org.h2.server.web.WebServlet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@CommonsLog
@Configuration
@ConditionalOnProperty(prefix = "spring.h2.console", name = "enabled", havingValue = "true", matchIfMissing = false)
public class H2ConsoleConfiguration {

    @Bean
    ServletRegistrationBean<WebServlet> h2ConsoleRegistrationBean(final ServletRegistrationBean<WebServlet> h2Console, final DataSourceProperties dataSourceProperties) {
        h2Console.setServlet(new WebServlet() {
            @Override
            public void init() {
                super.init();
                updateWebServlet(this, dataSourceProperties);
            }
        });
        return h2Console;
    }

    public static void updateWebServlet(final WebServlet webServlet, DataSourceProperties dataSourceProperties) {
        try {
            updateWebServer(getWebServer(webServlet), dataSourceProperties);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | NullPointerException ex) {
            log.error("Unable to set a custom ConnectionInfo for H2 console", ex);
        }
    }

    public static WebServer getWebServer(final WebServlet webServlet) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        final Field field = WebServlet.class.getDeclaredField("server");
        field.setAccessible(true);
        return (WebServer) field.get(webServlet);
    }

    public static void updateWebServer(final WebServer webServer, final DataSourceProperties dataSourceProperties) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final ConnectionInfo connectionInfo = new ConnectionInfo(String.format("Generic Spring Datasource|%s|%s|%s", dataSourceProperties.determineDriverClassName(), dataSourceProperties.determineUrl(), dataSourceProperties.determineUsername()));
        final Method method = WebServer.class.getDeclaredMethod("updateSetting", ConnectionInfo.class);
        method.setAccessible(true);
        method.invoke(webServer, connectionInfo);
    }
}