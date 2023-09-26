package de.footystats.tools.services.stats.jackson;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import de.footystats.tools.services.domain.Country;
import java.io.IOException;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class CountrySerializer extends JsonSerializer<Country> {

	@Override
	public void serialize(Country country, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		jsonGenerator.writeString(country.getCountryNameByFootystats());
	}
}
