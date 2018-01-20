package com.romantupikov.cloudstorage.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

@Configuration
public class GridFsTemplateConfig extends AbstractMongoConfiguration {

    @Value("${hvits3rk.mongo.address}")
    private String mongoAddress;

    @Value("${hvits3rk.mongo.database}")
    private String mongoDatabase;

    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
    }

    @Override
    protected String getDatabaseName() {
        return mongoDatabase;
    }

    @Override
    public MongoClient mongoClient() {
        return new MongoClient(mongoAddress);
    }
}
