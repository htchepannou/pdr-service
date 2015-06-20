package com.tchepannou.pdr.config;

import com.tchepannou.pdr.dao.DomainDao;
import com.tchepannou.pdr.dao.PartyDao;
import com.tchepannou.pdr.dao.impl.DomainDaoImpl;
import com.tchepannou.pdr.dao.impl.PartyDaoImpl;
import com.tchepannou.pdr.service.DomainService;
import com.tchepannou.pdr.service.PartyService;
import com.tchepannou.pdr.service.impl.DomainServiceImpl;
import com.tchepannou.pdr.service.impl.PartyServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class AppConfig {
    @Value("${database.driver}")
    private String driver;

    @Value ("${database.url}")
    private String url;

    @Value ("${database.username}")
    private String username;

    @Value ("${database.password}")
    private String password;


    //-- Beans
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }


    @Bean
    public DataSource dataSource (){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        return ds;
    }

    @Bean
    DomainService domainService () {
        return new DomainServiceImpl();
    }

    @Bean
    DomainDao domainDao () {
        return new DomainDaoImpl(dataSource());
    }

    @Bean
    PartyService partyService () {
        return new PartyServiceImpl();
    }

    @Bean
    PartyDao partyDao () {
        return new PartyDaoImpl(dataSource());
    }
    
}
