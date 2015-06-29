package com.tchepannou.pdr.config;

import com.tchepannou.pdr.dao.DomainDao;
import com.tchepannou.pdr.dao.PartyDao;
import com.tchepannou.pdr.dao.UserDao;
import com.tchepannou.pdr.dao.impl.DomainDaoImpl;
import com.tchepannou.pdr.dao.impl.PartyDaoImpl;
import com.tchepannou.pdr.dao.impl.UserDaoImpl;
import com.tchepannou.pdr.service.DomainService;
import com.tchepannou.pdr.service.PartyService;
import com.tchepannou.pdr.service.PasswordEncryptor;
import com.tchepannou.pdr.service.UserService;
import com.tchepannou.pdr.service.impl.DomainServiceImpl;
import com.tchepannou.pdr.service.impl.Md5PasswordEncryptor;
import com.tchepannou.pdr.service.impl.PartyServiceImpl;
import com.tchepannou.pdr.service.impl.UserServiceImpl;
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
    public PasswordEncryptor passwordEncryptor(){
        return new Md5PasswordEncryptor();
    }

    @Bean
    public DomainService domainService () {
        return new DomainServiceImpl();
    }

    @Bean
    public DomainDao domainDao () {
        return new DomainDaoImpl(dataSource());
    }

    @Bean
    public PartyService partyService () {
        return new PartyServiceImpl();
    }

    @Bean
    public PartyDao partyDao () {
        return new PartyDaoImpl(dataSource());
    }

    @Bean
    public UserService userService () {
        return new UserServiceImpl();
    }

    @Bean
    public UserDao userDao () {
        return new UserDaoImpl(dataSource());
    }
}
