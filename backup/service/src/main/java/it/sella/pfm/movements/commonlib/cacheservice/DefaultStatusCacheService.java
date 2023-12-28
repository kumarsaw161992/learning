package it.sella.pfm.movements.commonlib.cacheservice;

import it.sella.pfm.movements.commonlib.entity.DefaultStatus;
import lombok.CustomLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@CustomLog
@Service
public class DefaultStatusCacheService {

    @Autowired
    private HazelCastService<String, String> hazelCast;

    public void updateCache(DefaultStatus defaultStatus) {
        if (defaultStatus != null) {
            hazelCast.put(CacheName.DEFAULT_STATUS, defaultStatus.getOperationType(), defaultStatus.getStatus());
        }
    }

    public void removeFromCache(DefaultStatus defaultStatus) {
        if (defaultStatus != null) {
            hazelCast.remove(CacheName.DEFAULT_STATUS, defaultStatus.getOperationType());
        }
    }
}
