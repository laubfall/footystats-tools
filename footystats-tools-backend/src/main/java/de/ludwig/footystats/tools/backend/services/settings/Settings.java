package de.ludwig.footystats.tools.backend.services.settings;

import de.ludwig.footystats.tools.backend.mongo.converter.Password;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.mongodb.core.mapping.Document;

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
