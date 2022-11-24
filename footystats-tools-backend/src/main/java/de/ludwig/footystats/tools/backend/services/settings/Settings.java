package de.ludwig.footystats.tools.backend.services.settings;

import de.ludwig.footystats.tools.backend.mongo.Password;
import lombok.Getter;
import lombok.Setter;

public class Settings {
	@Getter
	@Setter
	private String footyStatsUsername;

	@Getter
	@Setter
	private Password footyStatsPassword;

}
