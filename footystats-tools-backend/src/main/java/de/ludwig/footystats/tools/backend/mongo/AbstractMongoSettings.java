package de.ludwig.footystats.tools.backend.mongo;

import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractMongoSettings implements MongoSettings{
    @Value("${mongodb.dbname}")
    private String dbName;

    @Override
    public String getDbName() {
        return dbName;
    }
}
