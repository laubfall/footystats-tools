package de.ludwig.footystats.tools.backend.mongo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.convert.ValueConverter;

@AllArgsConstructor
public class Password {
	@Getter
	@Setter
	private String password;
}
