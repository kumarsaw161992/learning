package it.sella.pfm.movements.commonlib.cacheservice;

import lombok.Getter;

import java.util.concurrent.TimeUnit;

@Getter
public enum CacheName {
    MOVEMENT_STATUS_CACHE("MOVEMENT_STATUS_CACHE", 30, TimeUnit.MINUTES),
    AGATA_CORRELATION_ID("AGATA_CORRELATION_ID", 48, TimeUnit.HOURS),
    CONFIG_PARAM("CONFIG_PARAM", 2, TimeUnit.HOURS),
    ACCOUNT_BALANCE("ACCOUNT_BALANCE", 2, TimeUnit.HOURS),
    CONCURRENT_LOCK("CONCURRENT_LOCK", 180, TimeUnit.SECONDS, false),
    SOLR_API_CACHE("SOLR_API_CACHE", 60, TimeUnit.SECONDS),
    DEFAULT_CATEGORY("DEFAULT_CATEGORY", 2, TimeUnit.HOURS),
    CAUSALE_INTERNA("CAUSALE_INTERNA", 2, TimeUnit.HOURS),
    DEFAULT_STATUS("DEFAULT_STATUS", 1, TimeUnit.HOURS),
    STATIC_CAUSALE("STATIC_CAUSALE", 1, TimeUnit.HOURS),
    CATEGORY_ALIAS("CATEGORY_ALIAS", 2, TimeUnit.HOURS),
    INSTRUMENT_TYPE("INSTRUMENT_TYPE", 2, TimeUnit.HOURS);

    private String name;

    private boolean isNearCacheRequired;

    private long ttl;

    private TimeUnit timeUnit;

    CacheName(String name, long ttl, TimeUnit timeUnit) {
        this(name, ttl, timeUnit, true);
    }

    CacheName(String name, long ttl, TimeUnit timeUnit, boolean isNearCacheRequired) {
        this.name = name;
        this.ttl = ttl;
        this.timeUnit = timeUnit;
        this.isNearCacheRequired = isNearCacheRequired;
    }


}