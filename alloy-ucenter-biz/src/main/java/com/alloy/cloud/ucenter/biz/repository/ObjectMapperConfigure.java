package com.alloy.cloud.ucenter.biz.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: tankechao
 * @Date: 2020/10/24 13:29
 */
@Configuration
public class ObjectMapperConfigure {
    @Bean
    public ObjectMapper objectMapper() {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(simpleModule());
        return objectMapper;
    }

    private SimpleModule simpleModule() {
        ParsedStringTermsBucketSerializer serializer = new ParsedStringTermsBucketSerializer(ParsedStringTerms.ParsedBucket.class);
        SimpleModule module = new SimpleModule();
        module.addSerializer(serializer);
        return module;
    }
}
