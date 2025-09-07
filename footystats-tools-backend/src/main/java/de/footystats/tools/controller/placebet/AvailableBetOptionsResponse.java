package de.footystats.tools.controller.placebet;

import de.footystats.tools.services.bet.attributes.Attribute;
import de.footystats.tools.services.prediction.Bet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AvailableBetOptionsResponse implements Serializable {
	private final List<Option> options = new ArrayList<>();

	public List<Bet> availableBets() {
		return options.stream().map(Option::bet).distinct().toList();
	}

	// TODO possible values for the attributes need to be transmitted to the frontend (e.g. all countries, all leagues, etc.).
	public record Option(Bet bet, List<Attribute> attribute) implements Serializable {

	}
}
