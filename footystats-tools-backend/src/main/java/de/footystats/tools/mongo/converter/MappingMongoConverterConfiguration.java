package de.footystats.tools.mongo.converter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MappingMongoConverterConfiguration {
    @Bean
    public MappingMongoConverter mongoConverter(MongoDatabaseFactory dbFactory, MongoMappingContext mongoMappingContext, ConverterRegistry converterRegistry) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(dbFactory);
        MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
        mongoConverter.setCustomConversions(customConversions(converterRegistry));
        return mongoConverter;
    }

    private MongoCustomConversions customConversions(ConverterRegistry converterRegistry) {
        List<Object> converters = new ArrayList<>();
        converters.addAll(Jsr310Converters.getConvertersToRegister());
		converters.addAll(converterRegistry.converters());
        return new MongoCustomConversions(converters);
    }
}
