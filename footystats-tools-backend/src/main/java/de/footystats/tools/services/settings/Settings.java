package de.footystats.tools.services.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Stores user specific settings for the application.
 */
@NoArgsConstructor
@AllArgsConstructor
@Document
@Getter
@Setter
public class Settings {

	private String _id;

	private String footyStatsUsername;

	private Password footyStatsPassword;

}
