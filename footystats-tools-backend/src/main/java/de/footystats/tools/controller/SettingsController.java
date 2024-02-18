package de.footystats.tools.controller;

import de.footystats.tools.services.footy.SessionCookieCache;
import de.footystats.tools.services.settings.Settings;
import de.footystats.tools.services.settings.SettingsRepository;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settings")
public class SettingsController {

	private final SettingsRepository settingsRepository;

	private final SessionCookieCache sessionCookieCache;

	public SettingsController(SettingsRepository settingsRepository, SessionCookieCache sessionCookieCache) {
		this.settingsRepository = settingsRepository;
		this.sessionCookieCache = sessionCookieCache;
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PostMapping(consumes = {"application/json"}, produces = {"application/json"})
	public void saveSettings(@RequestBody Settings settings) {
		Optional<Settings> any = settingsRepository.findAll().stream().findAny();
		if (any.isEmpty()) {
			settingsRepository.insert(settings);
			return;
		}

		var existingSettings = any.get();
		existingSettings.setFootyStatsPassword(settings.getFootyStatsPassword());
		existingSettings.setFootyStatsUsername(settings.getFootyStatsUsername());
		settingsRepository.save(existingSettings);
		sessionCookieCache.invalidateCookie(settings.getFootyStatsUsername());
	}

	@GetMapping(produces = {"application/json"})
	public Settings loadSettings() {
		Optional<Settings> any = settingsRepository.findAll().stream().findAny();
		return any.orElseGet(Settings::new);
	}
}
