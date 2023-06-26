package de.footystats.tools.controller;

import de.footystats.tools.services.settings.Settings;
import de.footystats.tools.services.settings.SettingsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/settings")
public class SettingsController {

	private final SettingsRepository settingsRepository;

	public SettingsController(SettingsRepository settingsRepository) {
		this.settingsRepository = settingsRepository;
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PostMapping(consumes = { "application/json" }, produces = { "application/json" })
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
	}

	@GetMapping(produces = { "application/json" })
	public Settings loadSettings() {
		Optional<Settings> any = settingsRepository.findAll().stream().findAny();
		if (any.isEmpty()) {
			return new Settings();
		}
		return any.get();
	}
}
