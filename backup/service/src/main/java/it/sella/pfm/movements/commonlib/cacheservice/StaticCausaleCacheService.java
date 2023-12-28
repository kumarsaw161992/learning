package it.sella.pfm.movements.commonlib.cacheservice;

import it.sella.pfm.movements.commonlib.entity.StaticCausale;
import lombok.CustomLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@CustomLog
@Service
public class StaticCausaleCacheService {

    @Autowired
    private HazelCastService<String, String> hazelCast;

    public void updateCache(StaticCausale staticCausale) {
        if (staticCausale != null) {
            hazelCast.put(CacheName.STATIC_CAUSALE, staticCausale.getCausale(), staticCausale.getCausaleToBe());
        }
    }

    public void removeFromCache(StaticCausale causale) {
        if (causale != null) {
            hazelCast.remove(CacheName.STATIC_CAUSALE, causale.getCausale());
        }
    }
}
