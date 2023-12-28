package it.sella.pfm.movements.commonlib.backoffice.service.categoryaliasmap;

import it.sella.pfm.movements.commonlib.backoffice.service.BaseService;
import it.sella.pfm.movements.commonlib.cacheservice.CategoryAliasMapCacheService;
import it.sella.pfm.movements.commonlib.entity.CategoryAliasMap;
import it.sella.pfm.movements.commonlib.exception.FabrickPFMMovementsCommonException;
import it.sella.pfm.movements.commonlib.exception.StatusCode;
import it.sella.pfm.movements.commonlib.mongoservice.BackofficeMongoService;
import it.sella.pfm.movements.commonlib.to.CategoryAliasMapDTO;
import it.sella.pfm.movements.commonlib.utils.SequenceUtil;
import lombok.CustomLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@CustomLog
@Service
public class CategoryAliasMapServiceImpl extends BaseService<CategoryAliasMap, CategoryAliasMapDTO> implements CategoryAliasMapService {

    private final BackofficeMongoService<CategoryAliasMap> mongoService;

    @Autowired
    public CategoryAliasMapServiceImpl(BackofficeMongoService<CategoryAliasMap> mongoService) {
        super(mongoService);
        this.mongoService = mongoService;
    }

    @Autowired
    CategoryAliasMapCacheService categoryAliasMapCacheService;

    @Override
    protected CategoryAliasMap getEntityObject() {
        return new CategoryAliasMap();
    }

    @Override
    protected CategoryAliasMapDTO getDTOObject() {
        return new CategoryAliasMapDTO();
    }

    @Override
    protected String getServiceName() {
        return this.getClass().getName();
    }

    @Override
    protected Class<CategoryAliasMap> getClassName() {
        return CategoryAliasMap.class;
    }

    @Override
    protected CategoryAliasMap updateSequence(CategoryAliasMap save) {
        Long id = mongoService.nextVal(SequenceUtil.CATEGORY_ALIAS_MAP).block();
        save.setId(id);
        return save;
    }

    @Override
    protected String getCollectionName() {
        return SequenceUtil.CATEGORY_ALIAS_MAP;
    }


    @Override
    protected Criteria getSearchCriteria(CategoryAliasMapDTO dto) {
        List<Criteria> criterias = new ArrayList<>();

        if (dto.getId() != null && dto.getId() > 0) {
            criterias.add(Criteria.where("_id").is(dto.getId()));
        }
        if (StringUtils.isNotEmpty(dto.getDirection())) {
            criterias.add(Criteria.where("direction").is(dto.getDirection()));
        }
        if (StringUtils.isNotEmpty(dto.getCategory())) {
            criterias.add(Criteria.where("category").is(dto.getCategory()));
        }
        if (StringUtils.isNotEmpty(dto.getSubCategory())) {
            criterias.add(Criteria.where("subCategory").is(dto.getSubCategory()));
        }
        if (StringUtils.isNotEmpty(dto.getAliasCategory())) {
            criterias.add(Criteria.where("aliasCategory").is(dto.getAliasCategory()));
        }

        if (StringUtils.isNotEmpty(dto.getAliasSubCategory())) {
            criterias.add(Criteria.where("aliasSubCategory").is(dto.getAliasSubCategory()));
        }

        if (StringUtils.isNotEmpty(dto.getHypeCategory())) {
            criterias.add(Criteria.where("hypeCategory").is(dto.getHypeCategory()));
        }

        if (dto.getReverseDefault() != null) {
            criterias.add(Criteria.where("reverseDefault").is(dto.getReverseDefault()));
        }
        if (dto.getTypeId() != 0) {
            criterias.add(Criteria.where("typeId").is(dto.getTypeId()));
        }

        return criterias.size() == 0 ? new Criteria() : new Criteria().andOperator(criterias.toArray(new Criteria[0]));
    }

    @Override
    protected void validateRequest(CategoryAliasMapDTO dto) throws FabrickPFMMovementsCommonException {
        String operation = dto.getDirection();
        String category = dto.getCategory();
        String aliasCategory = dto.getAliasCategory();
        if (StringUtils.isEmpty(operation) || StringUtils.isEmpty(category) || StringUtils.isEmpty(aliasCategory)) {
            throw new FabrickPFMMovementsCommonException(StatusCode.INVALID_INPUT.getCode(),
                    "Invalid Input operation or category or aliasCategory is null/empty");
        }
        if (dto.getReverseDefault() == null) {
            dto.setReverseDefault(Boolean.FALSE);
        }
    }

    @Override
    public void updateCache(CategoryAliasMap categoryAliasMap) {
        try {
            categoryAliasMapCacheService.updateCache(categoryAliasMap);
        } catch (Exception e) {
            log.error("Exception while updating the cache " + e.getMessage());
        }
    }

    @Override
    public void removeCache(CategoryAliasMap categoryAliasMap) {
        try {
            categoryAliasMapCacheService.removeFromCache(categoryAliasMap);
        } catch (Exception e) {
            log.error("Exception while removing from cache " + e.getMessage());
        }
    }
}
