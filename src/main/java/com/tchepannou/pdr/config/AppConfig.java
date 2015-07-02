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
        return new PasswordEncryptorImpl();
    }

    @Bean
    public AccessTokenService accessTokenService () {
        return new AccessTokenServiceImpl();
    }

    @Bean
    public ContactMechanismPurposeDao contactMechanismPurposeDao (){
        return new ContactMechanismPurposeDaoImpl(dataSource());
    }

    @Bean
    public ContactMechanismTypeDao contactMechanismTypeDao (){
        return new ContactMechanismTypeDaoImpl(dataSource());
    }

    @Bean
    public DomainService domainService () {
        return new DomainServiceImpl();
    }

    @Bean
    public DomainUserService domainUserService () {
        return new DomainUserServiceImpl();
    }

    @Bean
    public ElectronicAddressService electronicAddressService (){
        return new ElectronicAddressServiceImpl();
    }

    @Bean
    public PartyService partyService () {
        return new PartyServiceImpl();
    }

    @Bean
    public PartyElectronicAddressService partyElectronicAddressService() {
        return new PartyElectronicAddressServiceImpl();
    }

    @Bean
    public PartyPhoneService partyPhoneService() {
        return new PartyPhoneServiceImpl();
    }

    @Bean
    public PartyPostalAddressService partyPostalAddressService() {
        return new PartyPostalAddressServiceImpl();
    }

    @Bean
    public PermissionService permissionService () {
        return new PermissionServiceImpl();
    }

    @Bean
    public PhoneService phoneService () {
        return new PhoneServiceImpl();
    }

    @Bean
    public PostalAddressService postalAddressService () {
        return new PostalAddressServiceImpl();
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
    public AccessTokenDao accessTokenDao () {
        return new AccessTokenDaoImpl(dataSource());
    }

    @Bean
    public ContactMechanismTypeService contactMechanismTypeService() {
        return new ContactMechanismTypeServiceImpl();
    }

    @Bean
    public ContactMechanismPurposeService contactMechanismPurposeService (){
        return new ContactMechanismPurposeServiceImpl();
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
    public ElectronicAddressDao electronicAddressDao() {
        return new ElectronicAddressDaoImpl(dataSource());
    }

    @Bean
    public PartyDao partyDao () {
        return new PartyDaoImpl(dataSource());
    }

    @Bean
    public PartyElectronicAddressDao partyElectronicAddressDao() {
        return new PartyElectronicAddressDaoImpl(dataSource());
    }

    @Bean
    public PartyPhoneDao partyPhoneDao() {
        return new PartyPhoneDaoImpl(dataSource());
    }
    
    @Bean
    public PartyPostalAddressDao partyPostalAddressDao() {
        return new PartyPostalAddressDaoImpl(dataSource());
    }

    @Bean
    public PartyRoleTypeDao partyRoleTypeDao() {
        return new PartyRoleTypeDaoImpl(dataSource());
    }
    
    @Bean
    public PermissionDao permissionDao () {
        return new PermissionDaoImpl(dataSource());
    }

    @Bean
    public PhoneDao phoneDao () {
        return new PhoneDaoImpl(dataSource());
    }

    @Bean
    public PostalAddressDao postalAddressDao () {
        return new PostalAddressDaoImpl(dataSource());
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
