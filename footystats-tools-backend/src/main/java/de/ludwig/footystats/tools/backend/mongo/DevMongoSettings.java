package de.ludwig.footystats.tools.backend.mongo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
//@Profile("dev")
public class DevMongoSettings extends AbstractMongoSettings {

}
