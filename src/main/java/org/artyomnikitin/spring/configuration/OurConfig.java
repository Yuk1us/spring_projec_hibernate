package org.artyomnikitin.spring.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.*;

import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "org.artyomnikitin.spring")
@PropertySource("classpath:hibernate.properties")
@EnableWebMvc
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class OurConfig implements EnvironmentAware {


    private Environment environment;
    /**1.05v Deleted HardCoded vars
     * <br>
     * Added EnvironmentAware for reading Connection Properties from hibernate.properties
     * */
    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**Configure PostgreSQL Connection*/
    @Bean
    public DataSource dataSource(){

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(environment.getRequiredProperty("hibernate.connection.driver_class"));
            dataSource.setJdbcUrl(environment.getRequiredProperty("hibernate.connection.url"));
            dataSource.setUser(environment.getRequiredProperty("hibernate.connection.username"));
            dataSource.setPassword(environment.getRequiredProperty("hibernate.connection.password"));
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        return dataSource;

    }


    /**Initializes SessionFactory*/
    @Bean
    public HibernateTransactionManager transactionManager(){
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactoryBean().getObject());//Unwrap
        return transactionManager;
    }


    /**Configure SessionFactory Properties
     * @return FactoryBean{SessionFactory} */
    @Bean
    public LocalSessionFactoryBean sessionFactoryBean(){
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan("org.artyomnikitin.spring.dto");
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", environment.getRequiredProperty("hibernate.connection.dialect"));
        hibernateProperties.setProperty("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));

        sessionFactoryBean.setHibernateProperties(hibernateProperties);
        return sessionFactoryBean;
    }


}
