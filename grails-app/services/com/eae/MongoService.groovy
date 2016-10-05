package com.eae

import com.mongodb.MongoClient
import com.mongodb.BasicDBObject
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import grails.transaction.Transactional
import mongo.MongoFactory
import org.bson.Document
import org.json.JSONObject

import static com.mongodb.client.model.Filters.*;

import java.security.MessageDigest

@Transactional
class MongoService {

    /**
     * The method retrieves the specified mongo collection
     * @param mongoURL
     * @param dbName
     * @param collectionName
     * @return {MongoCollection}
     */
    def getMongoCollection(String mongoIP, String mongoPort, String user, String userDatabase, char[] password, String dbName, String collectionName){
        MongoClient mongoClient = MongoFactory.getMongoConnection(mongoIP, mongoPort, user, userDatabase, password);
        MongoDatabase db = mongoClient.getDatabase( dbName );
        MongoCollection<Document> coll = db.getCollection(collectionName);
        return coll;
    }


    def checkUser(String mongoIP, String mongoPort, String user, String userDatabase, char[] password, String dbName, String collectionName, String userName, String userPwd){
        def res = "NOK";
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        def sh256Pwd = md.digest(userPwd.getBytes("UTF-8")).encodeHex().toString()


        MongoClient mongoClient = MongoFactory.getMongoConnection(mongoIP, mongoPort, user, userDatabase, password);
        MongoDatabase db = mongoClient.getDatabase( dbName );
        MongoCollection<Document> coll = db.getCollection(collectionName);

        def tot =  coll.find(eq("username",userName)).first()

        println(tot.toString())
//        if(tot.filter())

//        def result = new JSONObject(((Document)coll.find(query).first()).toJson())
        mongoClient.close()

        return res
    }


}
