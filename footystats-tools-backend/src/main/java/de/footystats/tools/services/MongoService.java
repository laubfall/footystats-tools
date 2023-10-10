package de.footystats.tools.services;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Query;

public abstract class MongoService<MONGO_DOC> {

	protected MongoTemplate mongoTemplate;

	protected MappingMongoConverter mappingMongoConverter;

	protected MongoService(MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter) {
		this.mongoTemplate = mongoTemplate;
		this.mappingMongoConverter = mappingMongoConverter;
	}

	public abstract Query upsertQuery(MONGO_DOC example);

	public abstract Class<MONGO_DOC> upsertType();

	public void upsert(MONGO_DOC somethingToPersist) {
		var doc = new Document();
		mappingMongoConverter.write(somethingToPersist, doc);
		mongoTemplate.upsert(upsertQuery(somethingToPersist), new BasicUpdate(doc), upsertType());
	}
}
