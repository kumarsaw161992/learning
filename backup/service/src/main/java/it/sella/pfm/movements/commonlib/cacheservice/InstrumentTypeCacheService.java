package it.sella.pfm.movements.commonlib.cacheservice;

import it.sella.pfm.movements.commonlib.entity.InstrumentType;
import lombok.CustomLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@CustomLog
@Service
public class InstrumentTypeCacheService {

    private static final String KEY_DELIMITER = "/";

    @Autowired
    private HazelCastService<String, String> hazelCast;

    public void updateCache(InstrumentType instrumentType) {
        if (instrumentType != null) {
            hazelCast.put(CacheName.INSTRUMENT_TYPE,  instrumentType.getOperationType(), instrumentType.getInstrumentType());
        }
    }

    public void removeFromCache(InstrumentType instrumentType) {
        if (instrumentType != null) {
            hazelCast.remove(CacheName.INSTRUMENT_TYPE, instrumentType.getOperationType());
        }
    }
}
