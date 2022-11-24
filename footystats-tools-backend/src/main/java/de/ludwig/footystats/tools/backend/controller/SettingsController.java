package de.ludwig.footystats.tools.backend.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/settings")
public class SettingsController {
	@PostMapping()
	public void saveSettings(){

	}

	@JsonComponent
	public class Settings{
		@Getter
		@Setter
		private String password;
	}
}
