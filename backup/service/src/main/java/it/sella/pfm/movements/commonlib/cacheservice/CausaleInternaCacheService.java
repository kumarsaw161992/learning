package it.sella.pfm.movements.commonlib.cacheservice;

import it.sella.pfm.movements.commonlib.entity.CausaleInterna;
import it.sella.pfm.movements.commonlib.mongoservice.BackofficeMongoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CausaleInternaCacheService {

    private static final String KEY_DELIMITER = ",";
    private static final String VALUE_DELIMITER = "|";

    @Autowired
    private HazelCastService<String, String> hazelCast;

    @Autowired
    BackofficeMongoService<CausaleInterna> mongoService;

    public void updateCache(final CausaleInterna interna) {
        String key = null;
        if (interna != null) {
            if (StringUtils.isNotBlank(interna.getCausaleInternaCode())) {
                key = interna.getCausaleInternaCode();
            } else {
                final String[] keys = {interna.getCausaleAbbreviation(), interna.getCausaleHost()};
                key = StringUtils.join(keys, KEY_DELIMITER);
            }
            hazelCast.put(CacheName.CAUSALE_INTERNA, key, getValue(interna));
        }
    }

    public void removeFromCache(final CausaleInterna causaleInterna) {
        String key = null;
        if (causaleInterna != null) {
            if (StringUtils.isNotBlank(causaleInterna.getCausaleInternaCode())) {
                key = causaleInterna.getCausaleInternaCode();
            } else {
                final String[] keys = {causaleInterna.getCausaleAbbreviation(), causaleInterna.getCausaleHost()};
                key = StringUtils.join(keys, KEY_DELIMITER);
            }
            hazelCast.remove(CacheName.CAUSALE_INTERNA, key);
        }
    }

    public void removeDefaultMappingFromCache(final CausaleInterna causaleInterna) {
        if (StringUtils.isNotBlank(causaleInterna.getCausaleInternaCode())) {
            final String causaleAbbr = causaleInterna.getCausaleAbbreviation();
            final String causaleHost = causaleInterna.getCausaleHost();
            CausaleInterna get = mongoService
                    .findByCausaleAbbreviationAndCausaleHostAndCausaleInternaCodeIsNull(causaleAbbr, causaleHost,CausaleInterna.class);
            if (get == null) {
                // No default mapping available in DB, so remove also from cache if any default
                // mapping available
                hazelCast.remove(CacheName.CAUSALE_INTERNA, StringUtils.join(causaleAbbr, KEY_DELIMITER, causaleHost));
            }
        }
    }

    private String getValue(CausaleInterna causaleInterna) {
        String value = StringUtils.join(causaleInterna.getCausaleInstrumentType(), VALUE_DELIMITER, causaleInterna.getCausaleStructured());
        if (causaleInterna.getOverride() != null) {
            value = StringUtils.join(value, VALUE_DELIMITER, causaleInterna.getOverride());
        }
        return value;
    }

}
