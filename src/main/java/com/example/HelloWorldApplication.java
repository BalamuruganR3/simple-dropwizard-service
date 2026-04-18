package com.example;

import com.example.resources.HelloWorldResource;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi3.JdbiFactory;
import org.apache.commons.text.lookup.StringLookupFactory;
import org.apache.commons.text.lookup.StringLookup;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldApplication.class);

    public static void main(String[] args) throws Exception {
        new HelloWorldApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        final Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            LOGGER.warn("Could not load config.properties, falling back to defaults/env: {}", e.getMessage());
        }

        @SuppressWarnings("unchecked")
        java.util.Map<String, Object> configMap = (java.util.Map) properties;

        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new org.apache.commons.text.StringSubstitutor(
                                StringLookupFactory.INSTANCE.mapStringLookup(configMap)
                        )
                )
        );
    }

    @Override
    public void run(HelloWorldConfiguration configuration, Environment environment) {
        final DataSourceFactory database = configuration.getDataSourceFactory();

        LOGGER.info("Starting Flyway migrations...");
        final Flyway flyway = Flyway.configure()
                .dataSource(database.getUrl(), database.getUser(), database.getPassword())
                .locations("db/migration")
                .load();

        final MigrateResult migrateResult = flyway.migrate();
        if (migrateResult.success) {
            LOGGER.info("Flyway migration successful. Current version: {}", migrateResult.targetSchemaVersion);
        } else {
            LOGGER.error("Flyway migration failed!");
        }

        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, database, "postgresql");

        environment.jersey().register(new HelloWorldResource());
    }
}
