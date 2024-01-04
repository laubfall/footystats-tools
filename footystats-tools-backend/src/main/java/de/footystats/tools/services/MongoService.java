package de.footystats.tools.services;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Query;

/**
 * Base class for all services that have to communicate with the mongodb.
 *
 * @param <MONGO_DOC> Type of the document. Every service that implement this class address one document type that resides in a mongodb collection.
 */
public abstract class MongoService<MONGO_DOC> {

	protected MongoTemplate mongoTemplate;

	protected MappingMongoConverter mappingMongoConverter;

	protected MongoService(MongoTemplate mongoTemplate, MappingMongoConverter mappingMongoConverter) {
		this.mongoTemplate = mongoTemplate;
		this.mappingMongoConverter = mappingMongoConverter;
	}

	/**
	 * Provide the document specific upsert query.
	 *
	 * @param example document to upsert.
	 * @return see description.
	 */
	public abstract Query upsertQuery(MONGO_DOC example);

	/**
	 * Provides the type of the document.
	 *
	 * @return See description, never null.
	 */
	public abstract Class<MONGO_DOC> upsertType();

	/**
	 * Does an upsert for the given document. If it is an update the whole document inside the db is updated, so make sure that the given document
	 * contains every field you want to persist.
	 *
	 * @param somethingToPersist The document to insert or persist.
	 */
	public void upsert(MONGO_DOC somethingToPersist) {
		var doc = new Document();
		mappingMongoConverter.write(somethingToPersist, doc);
		mongoTemplate.upsert(upsertQuery(somethingToPersist), new BasicUpdate(doc), upsertType());
	}
}
