package de.footystats.tools.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.footystats.tools.services.stats.MatchStats;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.ContextConfiguration;

@Disabled
@ContextConfiguration(classes = {JackonConfiguration.class, LocalDateTimeSerializer.class})
@JsonTest
class LocalDateTimeSerializerTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void serializeDateTime() throws JsonProcessingException {
		MatchStats matchStats = new MatchStats();
		LocalDateTime now = LocalDateTime.of(2020, 1, 1, 12, 0, 0);
		matchStats.setDateGmt(now);
		String serializedValue = objectMapper.writeValueAsString(matchStats);
		System.out.println(serializedValue);
		Assertions.assertTrue(serializedValue.contains("2020-01-01 12:00"));
	}
}
