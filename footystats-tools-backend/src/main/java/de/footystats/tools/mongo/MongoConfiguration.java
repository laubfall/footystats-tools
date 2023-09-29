package de.footystats.tools.mongo;

import com.mongodb.MongoClientSettings;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;

@Configuration
public class MongoConfiguration {


	@Bean
	public MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);
	}

	@Bean
	public MongoClientSettingsBuilderCustomizer customizer(ApplicationContext context) {
		Map<String, Codec> beansOfType = context.getBeansOfType(Codec.class);
		List<Codec<?>> codecs = new ArrayList(beansOfType.values());
		CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
			CodecRegistries.fromCodecs(codecs));
		return settings -> settings.codecRegistry(codecRegistry);
	}
}
