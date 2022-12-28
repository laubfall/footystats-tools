package de.ludwig.footystats.tools.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ludwig.footystats.tools.backend.services.match.Match;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityRevision;
import de.ludwig.footystats.tools.backend.services.stats.MatchStatus;
import de.ludwig.footystats.tools.backend.services.match.MatchRepository;
import de.ludwig.footystats.tools.backend.services.prediction.InfluencerResult;
import de.ludwig.footystats.tools.backend.services.prediction.PrecheckResult;
import de.ludwig.footystats.tools.backend.services.prediction.PredictionAnalyze;
import de.ludwig.footystats.tools.backend.services.prediction.PredictionResult;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest
@ContextConfiguration(classes = {Configuration.class})
@AutoConfigureDataMongo
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class MatchControllerTest {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    public void cleanUp() {
        matchRepository.deleteAll();
    }

    @BeforeEach
    public void cleanup() {
        matchRepository.deleteAll();
    }

    @Test
    public void doc() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders
                .post("/match/list")).andExpect(status().is(415)).andDo(document("home"));
    }

    @Test
    public void listMatchesForCountryAndLeague() throws Exception {
        var date = LocalDateTime.of(2022, 8, 1, 13, 0);
        var match = new Match();
        match.setCountry("Germany");
        match.setLeague("Bundesliga");
        match.setRevision(new PredictionQualityRevision(1));
        match.setDateGMT(date);
        matchRepository.insert(match);

        var request = new MatchController.ListMatchRequest();
        request.setCountry("Germany");
        request.setLeague("Bundesliga");
        request.setStart(date.minusDays(1));
        request.setEnd(date.plusDays(1));
        var paging = Paging.builder().page(0).size(10).build();
        request.setPaging(paging);

        var json = objectMapper.writeValueAsString(request);
        mockMvc.perform(RestDocumentationRequestBuilders
                        .post("/match/list").content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(rh -> rh.getResponse())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("elements", Is.is(Matchers.hasSize(1))))
                .andExpect(MockMvcResultMatchers.jsonPath("totalElements", Is.is(1)));
    }

    @Test
    public void listMatchesForAllCountries(@Autowired MockMvc mvc) throws Exception {
        var date = LocalDateTime.of(2022, 8, 1, 13, 0);
        var match = new Match();
        match.setCountry("Germany");
        match.setLeague("Bundesliga");
        match.setRevision(new PredictionQualityRevision(1));
        match.setDateUnix(date.getLong(ChronoField.ERA));
        match.setDateGMT(date);
        match.setAwayTeam("Blah");
        match.setHomeTeam("Blub");
        match.setFootyStatsUrl("fjlksdjflkds");
        match.setBttsYes(new PredictionResult(1.4f, true, PredictionAnalyze.SUCCESS, null));
        match.setO05(new PredictionResult(1.4f, true, PredictionAnalyze.SUCCESS, List.of(new InfluencerResult("testInfluencer", 1.4f, PrecheckResult.OK))));
        match.setState(MatchStatus.complete);
        match.setGoalsAwayTeam(2);
        match.setGoalsHomeTeam(1);
        matchRepository.insert(match);

        var request = new MatchController.ListMatchRequest();
        var paging = Paging.builder().page(0).size(10).build();
        request.setPaging(paging);

        var json = objectMapper.writeValueAsString(request);
        mvc.perform(RestDocumentationRequestBuilders
                        .post("/match/list").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("totalElements", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("elements", Is.is(Matchers.hasSize(1))))
                .andDo(rh -> {
                    var content = rh.getResponse().getContentAsString();
                    System.out.println(content);
                });
    }
}
