package de.footystats.tools.services.settings;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import support.MongoMappingConverterConfiguration;

@ActiveProfiles("test")
@DataMongoTest
@AutoConfigureDataMongo
@Import({MongoMappingConverterConfiguration.class})
public class SettingsRepositoryTest {

	@Autowired
	private SettingsRepository settingsRepository;

	@Test
	public void saveAndLoadWithEncryptedPassword() {
		var setting = new Settings();
		setting.setFootyStatsUsername("daniel");
		setting.setFootyStatsPassword(new Password("verystrongpassword"));

		settingsRepository.save(setting);

		var loadedSetting = settingsRepository.findAll().get(0);
		Assertions.assertEquals("verystrongpassword", loadedSetting.getFootyStatsPassword().getPassword());
	}
}
