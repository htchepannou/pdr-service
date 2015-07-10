package com.tchepannou.pds.config;

import com.tchepannou.pds.dao.*;
import com.tchepannou.pds.dao.impl.*;
import com.tchepannou.pds.service.*;
import com.tchepannou.pds.service.impl.*;
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
    public ContactMechanismPurposeDao contactMechanismPurposeDao (){
        return new ContactMechanismPurposeDaoImpl(dataSource());
    }

    @Bean
    public ContactMechanismTypeDao contactMechanismTypeDao (){
        return new ContactMechanismTypeDaoImpl(dataSource());
    }

    @Bean
    public PartyContactMechanismService contactMechanismService () {
        return new PartyContactMechanismServiceImpl();
    }

    @Bean
    public PartyDao partyDao () {
        return new PartyDaoImpl(dataSource());
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
    public ContactMechanismTypeService contactMechanismTypeService() {
        return new ContactMechanismTypeServiceImpl();
    }

    @Bean
    public ContactMechanismPurposeService contactMechanismPurposeService (){
        return new ContactMechanismPurposeServiceImpl();
    }

    @Bean
    public ElectronicAddressDao electronicAddressDao() {
        return new ElectronicAddressDaoImpl(dataSource());
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
    public PhoneDao phoneDao () {
        return new PhoneDaoImpl(dataSource());
    }

    @Bean
    public PostalAddressDao postalAddressDao () {
        return new PostalAddressDaoImpl(dataSource());
    }
}
