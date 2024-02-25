package de.footystats.tools.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.footystats.tools.services.domain.DomainDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Base class for controller tests that ramps up the necessary resources like data sources, platformtransaction manager, mongodb, etc.
 */
@ActiveProfiles("test")
@WebMvcTest
@ContextConfiguration(classes = {Configuration.class})
@AutoConfigureDataJpa // Set up the transaction manager used by spring batch.
@AutoConfigureTestDatabase // Set up the datasource used by spring batch.
@AutoConfigureDataMongo
public abstract class BaseControllerTest {

	@Autowired
	protected ObjectMapper objectMapper;
	@Autowired
	protected MockMvc mockMvc;
	@Autowired
	protected DomainDataService domainDataService;
}
