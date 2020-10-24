package com.alloy.cloud.ucenter.biz.repository;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;

import java.io.IOException;

/**
 * StringTerms 序列化处理
 * @Author: tankechao
 * @Date: 2020/10/24 13:30
 */
public class ParsedStringTermsBucketSerializer extends StdSerializer<ParsedStringTerms.ParsedBucket> {
    @Override
    public void serialize(ParsedStringTerms.ParsedBucket parsedBucket, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("aggregations", parsedBucket.getAggregations());
        jsonGenerator.writeObjectField("key", parsedBucket.getKey());
        jsonGenerator.writeStringField("keyAsString", parsedBucket.getKeyAsString());
        jsonGenerator.writeNumberField("docCount", parsedBucket.getDocCount());
        jsonGenerator.writeEndObject();
    }

    public ParsedStringTermsBucketSerializer(Class<ParsedStringTerms.ParsedBucket> t) {
        super(t);
    }
}
