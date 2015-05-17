package edu.sjsu.cmpe.cache.client;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Rendezvous<T> {

    private final HashFunction hashFunction;
    private final HashMap<Integer,T> serverList = new HashMap<Integer,T>();

    public Rendezvous(Collection<T> servers){

        this.hashFunction = Hashing.md5();
        for(T server: servers){
            add(server);
        }
    }
    public void add(T server) {

        int hash = hashFunction.newHasher().putString(server.toString()).hash().asInt();
        serverList.put(hash, server);

    }

    public T getCache(Object key){
        if(serverList.isEmpty()){
            return null;
        }
        Integer maxHash = Integer.MIN_VALUE;
        T maxserver = null;

        for (Map.Entry<Integer, T> server : serverList.entrySet()) {
            int temp = hashFunction.newHasher()
                    .putString(key.toString())
                    .putString(server.getValue().toString()).hash().asInt();

            if (temp > maxHash) {
                maxHash = temp;
                maxserver = server.getValue();

            }
        }

        return maxserver;
    }





}
