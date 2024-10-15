package com.sheep.ezloan.contact.storage.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.SQLException;

@Configuration
public class CoreDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "storage.datasource.core")
    public HikariConfig coreHikariConfig() {
        return new HikariConfig();
    }

    @Bean
    @Profile("!local")
    public HikariDataSource coreDataSource(@Qualifier("coreHikariConfig") HikariConfig config) {
        return new HikariDataSource(config);
    }

    @Bean
    @Profile("local")
    public HikariDataSource localCoreDataSource(@Qualifier("coreHikariConfig") HikariConfig config)
            throws SQLException {
        Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9094").start();
        return new HikariDataSource(config);
    }

}
