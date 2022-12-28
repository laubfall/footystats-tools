package de.ludwig.footystats.tools.backend.mongo.converter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.convert.ValueConverter;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Document
public class Password {
	@Getter
	@Setter
	private String password;
}
