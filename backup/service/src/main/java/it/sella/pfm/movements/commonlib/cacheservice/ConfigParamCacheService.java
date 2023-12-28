package it.sella.pfm.movements.commonlib.cacheservice;

import it.sella.pfm.movements.commonlib.entity.ConfigParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigParamCacheService {

    @Autowired
    private HazelCastService<String, String> hazelCast;

    public void updateCache(ConfigParam configParam) {
        if (configParam != null) {
            hazelCast.put(CacheName.CONFIG_PARAM, configParam.getName(), configParam.getValue());
        }
    }

    public void removeFromCache(ConfigParam causale) {
        if (causale != null) {
            hazelCast.remove(CacheName.CONFIG_PARAM,causale.getName());
        }
    }
}
