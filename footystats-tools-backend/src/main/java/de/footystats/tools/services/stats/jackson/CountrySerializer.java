package de.footystats.tools.services.stats.jackson;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import de.footystats.tools.services.domain.Country;
import java.io.IOException;
import org.springframework.boot.jackson.JsonComponent;

/**
 * Custom serializer for {@link Country}.
 * <p>
 * This serializer is used to serialize {@link Country} objects to JSON because we only want the country name to be serialized.
 * <p>
 * Serializer is pickup up automatically via springs dependency injection mechanism.
 */
@JsonComponent
public class CountrySerializer extends JsonSerializer<Country> {

	@Override
	public void serialize(Country country, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		jsonGenerator.writeString(country.getCountryNameByFootystats());
	}
}
