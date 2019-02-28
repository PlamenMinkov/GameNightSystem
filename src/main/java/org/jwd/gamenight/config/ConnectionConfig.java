package org.jwd.gamenight.config;

import java.beans.PropertyVetoException;
import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories
@Import(RepositoryRestMvcConfiguration.class)
@SpringBootApplication
@EnableAutoConfiguration
public class ConnectionConfig {
    @Autowired
    private ApplicationContext applicationContext;

    @Bean(name = { "dataSource" }, destroyMethod = "close")
    protected DataSource dataSource() throws PropertyVetoException {
        final HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(applicationContext.getEnvironment()
                .getProperty("spring.datasource.driver-class-name"));
        dataSource.setJdbcUrl(applicationContext.getEnvironment().getProperty(
                "spring.datasource.url"));
        dataSource.setUsername(applicationContext.getEnvironment().getProperty(
                "spring.datasource.username"));
        dataSource.setPassword(applicationContext.getEnvironment().getProperty(
                "spring.datasource.password"));

        return dataSource;
    }

    @Bean(name = { "jpaVendorAdapter" })
    protected JpaVendorAdapter defaultJpaVendorAdapter() {
        final HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(false);
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter
                .setDatabasePlatform(org.hibernate.dialect.MySQL5Dialect.class
                        .getCanonicalName());

        return hibernateJpaVendorAdapter;
    }

    @Bean(name = { "entityManagerFactory" })
    protected EntityManagerFactory entityManagerFactory(DataSource dataSource,
                                                        Environment env) throws PropertyVetoException, IOException {
        final LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setJpaVendorAdapter(defaultJpaVendorAdapter());
        entityManagerFactory.setJpaDialect(new HibernateJpaDialect());
        entityManagerFactory
                .setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
        entityManagerFactory
                .setPersistenceProviderClass(org.hibernate.jpa.HibernatePersistenceProvider.class);

        entityManagerFactory.afterPropertiesSet();

        return entityManagerFactory.getObject();
    }
}
