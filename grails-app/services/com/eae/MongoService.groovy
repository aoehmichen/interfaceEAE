package com.eae

import com.mongodb.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import grails.transaction.Transactional
import mongo.MongoFactory
import org.bson.Document

@Transactional
class MongoService {

    /**
     * The method retrieves the specified mongo collection
     * @param mongoURL
     * @param dbName
     * @param collectionName
     * @return {MongoCollection}
     */
    def getMongoCollection(String mongoIP, String mongoPort, String user, String userDatabse, char[] password, String dbName, String collectionName){
        MongoClient mongoClient = MongoFactory.getMongoConnection(mongoIP, mongoPort, user, userDatabse, password);
        MongoDatabase db = mongoClient.getDatabase( dbName );
        MongoCollection<Document> coll = db.getCollection(collectionName);
        return coll;
    }

}
