package de.footystats.tools.services.match;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchSearch {

	@Getter
	@Setter
	private List<String> fullTextSearchTerms;

	@Getter
	@Setter
	private List<String> countries;

	@Getter
	@Setter
	private List<String> leagues;

	@Getter
	@Setter
	private LocalDateTime start;

	@Getter
	@Setter
	private LocalDateTime end;

	@Getter
	@Setter
	private Pageable pageable;
}
