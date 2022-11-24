package de.ludwig.footystats.tools.backend.services.settings;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SettingsRepository extends MongoRepository<Settings, String> {
}
