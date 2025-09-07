package de.footystats.tools.controller.placebet;

import de.footystats.tools.services.bet.attributes.Attribute;
import de.footystats.tools.services.bet.attributes.AttributeSeries;
import de.footystats.tools.services.bet.attributes.AttributeSeriesService;
import de.footystats.tools.services.bet.attributes.BaseBetAttribute;
import de.footystats.tools.services.bet.attributes.BetAttribute;
import de.footystats.tools.services.prediction.Bet;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/placebet")
public class PlaceBetController {

	private final AttributeSeriesService attributeSeriesService;

	public PlaceBetController(AttributeSeriesService attributeSeriesService) {
		this.attributeSeriesService = attributeSeriesService;
	}

	@GetMapping(value = "/available-options", produces = "application/json")
	public AvailableBetOptionsResponse getAvailableBetOptions() {
		var response = new AvailableBetOptionsResponse();
		for (Bet bet : Bet.values()) {
			List<AttributeSeries> attributeSeries = attributeSeriesService.by(bet);
			attributeSeries.forEach(as -> mapSeriesToResponse(bet, as, response));
		}

		return response;
	}

	private void mapSeriesToResponse(Bet bet, AttributeSeries series, AvailableBetOptionsResponse response) {
		if (!CollectionUtils.isEmpty(series.getAttributes())) {
			List<Attribute> attributes = series.getAttributes().stream().filter(
				a -> !(a instanceof BetAttribute)).map(BaseBetAttribute::getName).toList();
			response.getOptions().add(new AvailableBetOptionsResponse.Option(bet, attributes));
		} else {
			response.getOptions().stream().filter(o -> o.bet().equals(bet)).findAny().orElseGet(() -> {
				response.getOptions().add(new AvailableBetOptionsResponse.Option(bet, List.of()));
				return null;
			});
		}
	}
}
