package de.ludwig.footystats.tools.backend;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("footystats")
public class FootystatsProperties {

    @Getter
    @Setter
    private String allowedOrigins;
}
