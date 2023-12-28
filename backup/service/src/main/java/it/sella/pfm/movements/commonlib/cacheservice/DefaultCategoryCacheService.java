package it.sella.pfm.movements.commonlib.cacheservice;

import it.sella.pfm.movements.commonlib.entity.DefaultCategory;
import it.sella.pfm.movements.commonlib.mongoservice.BackofficeMongoService;
import it.sella.pfm.movements.commonlib.utils.JSONUtil;
import lombok.CustomLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@CustomLog
@Service
public class DefaultCategoryCacheService {

    private static final String KEY_DELIMITER = "/";

    @Autowired
    private HazelCastService<String, String> hazelCast;

    @Autowired
    BackofficeMongoService<DefaultCategory> mongoService;

    public DefaultCategory get(String direction, String operationType) {
        try {
            String cacheKey = getKey(direction, null, operationType);
            String jsonStringValue = hazelCast.get(CacheName.DEFAULT_CATEGORY, cacheKey);
            log.info("**** Got DefaultCategory from cache for key:" + cacheKey + " is :" + jsonStringValue);
            if (StringUtils.isNotBlank(jsonStringValue)) {
                DefaultCategory value = JSONUtil.getObjectFomJSONString(jsonStringValue, DefaultCategory.class);
                return value;
            }
            DefaultCategory defaultCategory = mongoService.findByDirectionAndOperationTypeAndOperationCodeIsNull(direction, operationType, DefaultCategory.class);
            if (defaultCategory != null) {
                jsonStringValue = JSONUtil.getObjectMapper().writeValueAsString(defaultCategory);
                log.info("**** Putting DefaultCategory to cache for key:" + getKey(defaultCategory) + " is :" + jsonStringValue);
                hazelCast.put(CacheName.DEFAULT_CATEGORY, getKey(defaultCategory), jsonStringValue);
                return defaultCategory;
            }
        } catch (Exception ex) {
            log.info("**** Exception inside DefaultCategoryCacheService::get ", ex);
        }
        return null;
    }

    public void update(DefaultCategory defaultCategory) {
        try {
            final String jsonStringValue = JSONUtil.getObjectMapper().writeValueAsString(defaultCategory);
            if (StringUtils.isNotBlank(jsonStringValue)) {
                final String cacheKey = getKey(defaultCategory);
                hazelCast.put(CacheName.DEFAULT_CATEGORY, cacheKey, jsonStringValue);
            }
        } catch (Exception ex) {
            log.info("**** Exception inside DefaultCategoryCacheService::update ", ex);
        }
    }

    public void remove(DefaultCategory defaultCategory) {
        try {
            String jsonStringValue = JSONUtil.getObjectMapper().writeValueAsString(defaultCategory);
            final String cacheKey = getKey(defaultCategory);
            log.info("**** removing DefaultCategory from cache for key:" + cacheKey + " is :" + jsonStringValue);
            hazelCast.remove(CacheName.DEFAULT_CATEGORY, cacheKey);
        } catch (Exception ex) {
            log.info("**** Exception inside DefaultCategoryCacheService::remove ", ex);
        }
    }


    public String getKey(DefaultCategory category) {
        return getKey(category.getDirection(), category.getOperationCode(), category.getOperationType());
    }

    public static String getKey(String direction, String operation, String operationType) {
        String key = null;
        if (StringUtils.isNotBlank(operation) && StringUtils.isNotBlank(operationType)) {
            key = StringUtils.join(direction, KEY_DELIMITER, operation, KEY_DELIMITER, operationType);
        } else if (StringUtils.isNotBlank(operation)) {
            key = StringUtils.join(direction, KEY_DELIMITER, operation);
        } else if (StringUtils.isNotBlank(operationType)) {
            key = StringUtils.join(direction, KEY_DELIMITER, operationType);
        }
        return key;
    }


}