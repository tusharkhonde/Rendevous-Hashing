package edu.sjsu.cmpe.cache.repository;

import edu.sjsu.cmpe.cache.domain.Entry;
import net.openhft.chronicle.map.ChronicleMapBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by TUSHAR_SK on 5/16/15.
 */
public class ChronicleMapCache implements CacheInterface {

    /* Use of Chronicle Map cache. (Key, Value) -> (Key, Entry) */

        File file = new File("/Users/TUSHAR_SK/chm.dat");

        ChronicleMapBuilder<Long, Entry> builder = ChronicleMapBuilder.of(Long.class, Entry.class);
        ConcurrentMap<Long, Entry> chronicleMap = builder.createPersistedTo(file);


       public ChronicleMapCache(ConcurrentHashMap<Long, Entry> entries) throws IOException {
                  chronicleMap = entries;

        }

    @Override
    public Entry save(Entry newEntry) {
        checkNotNull(newEntry, "newEntry instance must not be null");
        chronicleMap.putIfAbsent(newEntry.getKey(), newEntry);

        return newEntry;
    }

    @Override
    public Entry get(Long key) {
        checkArgument(key > 0,"Key was %s but expected greater than zero value", key);
        return chronicleMap.get(key);
    }

    @Override
    public List<Entry> getAll() {
        return new ArrayList<Entry>(chronicleMap.values());
    }
}
