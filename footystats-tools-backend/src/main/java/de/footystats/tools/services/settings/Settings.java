package de.footystats.tools.services.settings;

import de.footystats.tools.mongo.converter.Password;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Document
public class Settings {

	@Getter
	@Setter
	private String _id;

	@Getter
	@Setter
	private String footyStatsUsername;

	@Getter
	@Setter
	private Password footyStatsPassword;

}
