package it.sella.pfm.movements.commonlib.cacheservice;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import lombok.CustomLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

@CustomLog
@Service
public class HazelCastService<K, V extends Serializable> {


    public void put(CacheName name, K key, V value) {
        try {
            this.instance.getMap(name.getName()).putAsync(key, value, name.getTtl(), name.getTimeUnit());
        } catch (Exception e) {
            log.error("Error occured while putting value in Hazelcast", e);
        }
    }

    public void putAll(CacheName name, Map<K, V> map) {
        try {
            IMap<K, V> imap = this.instance.getMap(name.getName());
            map.entrySet().forEach(e -> {
                imap.putAsync(e.getKey(), e.getValue(), name.getTtl(), name.getTimeUnit());
            });
        } catch (Exception e) {
            log.error("Error occured while putting value in Hazelcast", e);
        }
    }

    public V get(CacheName name, K key) {
        try {
            return this.instance.<K, V>getMap(name.getName()).get(key);
        } catch (Exception e) {
            log.error("Error occured while getting value from Hazelcast", e);
            return null;
        }
    }

    public Map<K, V> getAll(CacheName name, Set<K> keys) {
        try {
            return this.instance.<K, V>getMap(name.getName()).getAll(keys);
        } catch (Exception e) {
            log.error("Error occured while getting value from Hazelcast", e);
            return Collections.emptyMap();
        }
    }

    public Map<K, V> getAll(CacheName name, List<K> keys) {
        return getAll(name, new HashSet<>(keys));
    }

    public void remove(CacheName name, K key) {
        try {
            this.instance.<K, V>getMap(name.getName()).remove(key);
        } catch (Exception e) {
            log.error("Error occured while removing value from Hazelcast", e);
        }
    }

    public V putIfAbsent(CacheName name, K key, V value) {
        try {
            return this.instance.<K, V>getMap(name.getName())
                    .putIfAbsent(key, value, name.getTtl(), name.getTimeUnit());
        } catch (Exception e) {
            log.error("Error occured while putting value on Hazelcast", e);
            return null;
        }
    }


    @Autowired
    private HazelcastInstance instance;

    public void destroyMap(String map) {
        try {
            instance.getMap(map).destroy();
        } catch (Exception e) {
            log.error(String.format("Error occured while destroying map %s on Hazelcast", map), e);
        }
    }

}