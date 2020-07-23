package pl.chessWebApp.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/wojtek?useSSL=false&serverTimezone=UTC");
        dataSourceBuilder.username("wojtek");
        dataSourceBuilder.password("op9p6a!@dWeZu");
        return dataSourceBuilder.build();
    }
}