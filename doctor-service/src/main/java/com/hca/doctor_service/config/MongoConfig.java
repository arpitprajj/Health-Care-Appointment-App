package com.hca.doctor_service.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoConfig {

    private static final String URI =
            "mongodb://root:root@localhost:27017/health?authSource=admin";

    @Bean
    public MongoClient mongoClient() {

        ConnectionString connectionString =
                new ConnectionString(URI);

        MongoClientSettings settings =
                MongoClientSettings.builder()
                        .applyConnectionString(connectionString)
                        .build();

        return MongoClients.create(settings);
    }

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory(
            MongoClient mongoClient) {

        return new SimpleMongoClientDatabaseFactory(
                mongoClient,
                "health"
        );
    }

    @Bean
    public MongoTemplate mongoTemplate(
            MongoDatabaseFactory factory) {

        return new MongoTemplate(factory);
    }
}