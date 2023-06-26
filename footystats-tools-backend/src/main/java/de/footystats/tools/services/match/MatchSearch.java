package de.footystats.tools.services.match;

import lombok.*;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchSearch {
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
