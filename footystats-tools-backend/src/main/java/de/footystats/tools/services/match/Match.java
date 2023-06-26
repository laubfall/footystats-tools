package de.footystats.tools.services.match;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.footystats.tools.services.stats.MatchStatus;
import de.footystats.tools.services.prediction.quality.PredictionQualityRevision;
import de.footystats.tools.services.prediction.PredictionResult;
import lombok.*;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Builder()
@AllArgsConstructor
@NoArgsConstructor
@JsonComponent
@CompoundIndexes({
        @CompoundIndex(name = "unique", def = "{'dateUnix' : 1, 'dateGMT': 1, 'country': 1, 'league': 1, 'homeTeam': 1, 'awayTeam': 1, 'state': 1}")
})
public class Match {
    @Getter
    @Setter
    private Long dateUnix;
    @Getter
    @Setter
	@JsonFormat(pattern = "YYYY-MM-dd HH:mm'Z'")
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
