package pl.chessWebApp.security.config;

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
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/spring_security_custom_user_demo?useSSL=false&serverTimezone=UTC");
        dataSourceBuilder.username("hbstudent");
        dataSourceBuilder.password("hbstudent");
        return dataSourceBuilder.build();
    }
}