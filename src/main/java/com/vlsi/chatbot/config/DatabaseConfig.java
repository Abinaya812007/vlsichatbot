package com.vlsi.chatbot.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
    
    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    
    @Bean
    public DataSource dataSource() {
        SQLiteConfig sqLiteConfig = new SQLiteConfig();
        sqLiteConfig.setJournalMode(SQLiteConfig.JournalMode.WAL);
        sqLiteConfig.setSynchronous(SQLiteConfig.SynchronousMode.NORMAL);
        sqLiteConfig.enforceForeignKeys(true);
        
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setDriverClassName("org.sqlite.JDBC");
        config.setMaximumPoolSize(1); // SQLite supports only one connection for writes
        config.setConnectionTestQuery("SELECT 1");
        config.addDataSourceProperty("journal_mode", "WAL");
        
        return new HikariDataSource(config);
    }
}
