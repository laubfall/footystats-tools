package de.ludwig.footystats.tools.backend.services.settings;

import de.ludwig.footystats.tools.backend.mongo.converter.Password;
import de.ludwig.footystats.tools.backend.mongo.converter.PasswordReadingConverter;
import de.ludwig.footystats.tools.backend.services.EncryptionService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.convert.PropertyValueConverter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.ValueConverter;
import org.springframework.data.mongodb.core.convert.MongoConversionContext;
import org.springframework.data.mongodb.core.convert.MongoValueConverter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Document
@JsonComponent
public class Settings {
	@Getter
	@Setter
	private String footyStatsUsername;

	@Getter
	@Setter
	private Password footyStatsPassword;

}
