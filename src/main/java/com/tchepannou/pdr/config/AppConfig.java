package com.tchepannou.pdr.config;

import com.tchepannou.pdr.dao.*;
import com.tchepannou.pdr.dao.impl.*;
import com.tchepannou.pdr.service.*;
import com.tchepannou.pdr.service.impl.*;
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
    public PartyService partyService () {
        return new PartyServiceImpl();
    }

    @Bean
    public PermissionService permissionService () {
        return new PermissionServiceImpl();
    }

    @Bean
    public RoleService roleService () {
        return new RoleServiceImpl();
    }

    @Bean
    public UserService userService () {
        return new UserServiceImpl();
    }
    
    
    @Bean
    public DomainDao domainDao () {
        return new DomainDaoImpl(dataSource());
    }

    @Bean
    public DomainUserDao domainUserDao() {
        return  new DomainUserDaoImpl(dataSource());
    }

    @Bean
    public PartyDao partyDao () {
        return new PartyDaoImpl(dataSource());
    }

    @Bean
    public PermissionDao permissionDao () {
        return new PermissionDaoImpl(dataSource());
    }

    @Bean
    public RoleDao roleDao () {
        return new RoleDaoImpl(dataSource());
    }

    @Bean
    public UserDao userDao () {
        return new UserDaoImpl(dataSource());
    }
}
