package de.footystats.tools.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MongoConfiguration {

	@Profile("!test") // TX with embedded mongodb as used in unit tests are not possible.
	@Bean
	public MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);
	}

	@Bean
	public MappingMongoConverter mongoConverter(MongoDatabaseFactory dbFactory, MongoMappingContext mongoMappingContext, ApplicationContext context) {
		DbRefResolver dbRefResolver = new DefaultDbRefResolver(dbFactory);
		MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
		mongoConverter.setCustomConversions(customConversions(context));
		return mongoConverter;
	}

	private MongoCustomConversions customConversions(ApplicationContext context) {
		List<Object> converters = new ArrayList<>();
		converters.addAll(Jsr310Converters.getConvertersToRegister());
		Map<String, Converter> beansOfType = context.getBeansOfType(Converter.class);
		converters.addAll(beansOfType.values());
		return new MongoCustomConversions(converters);
	}
}
