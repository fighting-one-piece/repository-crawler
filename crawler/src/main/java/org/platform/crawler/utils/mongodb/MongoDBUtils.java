package org.platform.crawler.utils.mongodb;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class MongoDBUtils {

	private MongoClient mongoClient = null;  
    
    private MongoDBUtils() {  
        initMongoClient();  
    }  
      
    private static class MongoDBUtilsHolder {  
        private static final MongoDBUtils INSTANCE = new MongoDBUtils();  
    }  
      
    public static final MongoDBUtils getInstance() {  
        return MongoDBUtilsHolder.INSTANCE;  
    }  
      
    private void initMongoClient() {  
        List<ServerAddress> serverAddressList = new ArrayList<ServerAddress>();  
        ServerAddress serverAddress01 = new ServerAddress("192.168.0.20", 27018);  
        ServerAddress serverAddress02 = new ServerAddress("192.168.0.21", 27018);  
        serverAddressList.add(serverAddress01);  
        serverAddressList.add(serverAddress02);  
        mongoClient = new MongoClient(serverAddressList);  
    }  
      
    public MongoClient getClient() {  
        return mongoClient;  
    }  
    
}
