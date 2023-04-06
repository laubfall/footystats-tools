package de.ludwig.footystats.tools.backend.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@org.springframework.context.annotation.Configuration
public class JackonsConfiguration {
	@Bean
	public ObjectMapper objectMapper() {
		var objectMapper = new ObjectMapper();
		var javaTimeModule = new JavaTimeModule();
		objectMapper.registerModule(new JavaTimeModule());
		javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
		return objectMapper;
	}
}
