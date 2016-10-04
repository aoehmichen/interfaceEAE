package mongo

import com.mongodb.MongoClient
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress

public class MongoFactory {

    /**
     *
     * @param IPAdress
     * @param port
     * @return @return {MonClient} : client to be used for running the queries
     */
    static def getMongoConnection(String IPAddress, String port, String user, String database, char[] password){
        int portToUse = Integer.parseInt(port)
        ServerAddress address = new ServerAddress(IPAddress, portToUse)
        MongoCredential credential = MongoCredential.createCredential(user, database, password);
        List<MongoCredential> credentialList = new ArrayList<MongoCredential>();
        credentialList.add(credential)
        return new MongoClient(address, credentialList);
    }

}