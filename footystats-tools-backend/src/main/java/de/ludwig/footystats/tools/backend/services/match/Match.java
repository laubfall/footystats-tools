package de.ludwig.footystats.tools.backend.services.match;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.JsonTypeName;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityRevision;
import de.ludwig.footystats.tools.backend.services.stats.MatchStatus;
import de.ludwig.footystats.tools.backend.services.prediction.PredictionResult;
import lombok.*;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;

import java.time.LocalDateTime;

@Builder()
@AllArgsConstructor
@NoArgsConstructor
@JsonComponent
@CompoundIndexes({
        @CompoundIndex(name = "unique", def = "{'dateUnix' : 1, 'dateGMT': 1, 'country': 1, 'league': 1, 'homeTeam': 1, 'awayTeam': 1}")
})
public class Match {
    @Getter
    @Setter
    private Long dateUnix;
    @Getter
    @Setter
	@JsonFormat(pattern = "YYYY-MM-DD hh:mm")
    private LocalDateTime dateGMT;
    @Getter
    @Setter
    private String country;
    @Getter
    @Setter
    private String league;
    @Getter
    @Setter
    private String homeTeam;
    @Getter
    @Setter
    private String awayTeam;
    @Getter
    @Setter
    private Integer goalsHomeTeam;
    @Getter
    @Setter
    private Integer goalsAwayTeam;
    @Getter
    @Setter
    private MatchStatus state;
    @Getter
    @Setter
    private String footyStatsUrl;
    @Getter
    @Setter
    private PredictionResult o05;
    @Getter
    @Setter
    private PredictionResult bttsYes;
    @Getter
    @Setter
    private PredictionQualityRevision revision;
}
