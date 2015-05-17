package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client {

    public static void main(String[] args) throws Exception {


        ArrayList cacheServer = new ArrayList();

        cacheServer.add("http://localhost:3000");
        cacheServer.add("http://localhost:3001");
        cacheServer.add("http://localhost:3002");
        

      Rendezvous<String> r = new Rendezvous(cacheServer);
        
        System.out.println("Starting Rendezvous hashing");
        System.out.println("Adding to cache servers");
        for(int i = 1; i<=10; i++){
            putr(i, String.valueOf((char) (i + 96)), r);
        }

        System.out.println("Cache Retrieved from servers");
        for(int i =1; i<=10; i++){
            String value = (String)getr(i, r);
            System.out.println("Cache Collected : " + value);
        }

        
}
   
  // Rendezvous Hashing put/get
    public static void putr(int key, String value, Rendezvous r){
        String cacheUrl = (String) r.getCache(key);
        CacheServiceInterface cache = new DistributedCacheService(cacheUrl);
        cache.put(key,value);
        System.out.println("Putting to server:"+ cacheUrl);
        System.out.println("put( " + key + " => " + value + ")");

    }
    public static Object getr(int key, Rendezvous r){
        String cacheUrl = (String) r.getCache(key);
        CacheServiceInterface cache = new DistributedCacheService(cacheUrl);
        String value = cache.get(key);
        System.out.println("Received from server:"+cacheUrl);
        System.out.println("get( "+ key+ " ) => "+ value);
        return value;
    }
    

}
