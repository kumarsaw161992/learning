package it.sella.pfm.movements.commonlib.cacheservice;

import it.sella.pfm.movements.commonlib.entity.CategoryAliasMap;
import it.sella.pfm.movements.commonlib.utils.JSONUtil;
import lombok.CustomLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@CustomLog
@Service
public class CategoryAliasMapCacheService {

    private static final String KEY_DELIMITER = "_";

    @Autowired
    private HazelCastService<String, String> hazelCast;

    public void updateCache(CategoryAliasMap categoryAliasMap) {
        try {
            final String jsonStringValue = JSONUtil.getObjectMapper().writeValueAsString(categoryAliasMap);
            if (StringUtils.isNotBlank(jsonStringValue)) {
                final String cacheKey = getKey(categoryAliasMap);
                hazelCast.put(CacheName.CATEGORY_ALIAS, cacheKey, jsonStringValue);
            }
        } catch (Exception ex) {
            log.error("**** Exception inside DefaultCategoryCacheService::update ", ex);
        }
    }

    public void removeFromCache(CategoryAliasMap categoryAliasMap) {
        try {
            String jsonStringValue = JSONUtil.getObjectMapper().writeValueAsString(categoryAliasMap);
            final String cacheKey = getKey(categoryAliasMap);
            log.info("**** removing DefaultCategory from cache for key:" + cacheKey + " is :" + jsonStringValue);
            hazelCast.remove(CacheName.CATEGORY_ALIAS, cacheKey);
        } catch (Exception ex) {
            log.error("**** Exception inside DefaultCategoryCacheService::remove ", ex);
        }
    }

    private String getKey(CategoryAliasMap categoryAliasMap) {
        StringBuilder key = new StringBuilder(categoryAliasMap.getDirection()).append(KEY_DELIMITER).append(categoryAliasMap.getCategory());
        if (StringUtils.isNotEmpty(categoryAliasMap.getSubCategory())) {
            key.append(KEY_DELIMITER).append(categoryAliasMap.getSubCategory());
        }
        return key.toString();
    }

}