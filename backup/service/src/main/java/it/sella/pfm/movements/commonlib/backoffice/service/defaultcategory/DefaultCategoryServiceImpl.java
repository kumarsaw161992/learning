package it.sella.pfm.movements.commonlib.backoffice.service.defaultcategory;

import it.sella.pfm.movements.commonlib.cacheservice.DefaultCategoryCacheService;
import it.sella.pfm.movements.commonlib.entity.DefaultCategory;
import it.sella.pfm.movements.commonlib.exception.FabrickPFMMovementsCommonException;
import it.sella.pfm.movements.commonlib.exception.StatusCode;
import it.sella.pfm.movements.commonlib.mongoservice.BackofficeMongoService;
import it.sella.pfm.movements.commonlib.to.DefaultCategoryDTO;
import it.sella.pfm.movements.commonlib.backoffice.service.BaseService;
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
public class DefaultCategoryServiceImpl extends BaseService<DefaultCategory, DefaultCategoryDTO> implements DefaultCategoryService {

    private final BackofficeMongoService<DefaultCategory> mongoService;

    @Autowired
    public DefaultCategoryServiceImpl(BackofficeMongoService<DefaultCategory> mongoService) {
        super(mongoService);
        this.mongoService = mongoService;
    }

    @Autowired
    DefaultCategoryCacheService defaultCategoryCacheService;

    @Override
    protected DefaultCategory getEntityObject() {
        return new DefaultCategory();
    }

    @Override
    protected DefaultCategoryDTO getDTOObject() {
        return new DefaultCategoryDTO();
    }

    @Override
    protected String getServiceName() {
        return this.getClass().getName();
    }

    @Override
    protected Class<DefaultCategory> getClassName() {
        return DefaultCategory.class;
    }

    @Override
    protected DefaultCategory updateSequence(DefaultCategory save) {
        Long id = mongoService.nextVal(SequenceUtil.DEFAULT_CATEGORY).block();
        save.setId(id);
        return save;
    }

    @Override
    protected String getCollectionName() {
        return SequenceUtil.DEFAULT_CATEGORY;
    }

    @Override
    protected Criteria getSearchCriteria(DefaultCategoryDTO dto) {
        List<Criteria> criterias = new ArrayList<>();

        if (dto.getId() != null && dto.getId() > 0) {
            criterias.add(Criteria.where("_id").is(dto.getId()));
        }
        if (StringUtils.isNotEmpty(dto.getOperationType())) {
            criterias.add(Criteria.where("operationType").is(dto.getOperationType()));
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
        if (StringUtils.isNotEmpty(dto.getOperationCode())) {
            criterias.add(Criteria.where("operationCode").is(dto.getOperationCode()));
        }
        if (dto.getRecurrent() != null) {
            criterias.add(Criteria.where("recurrent").is(dto.getRecurrent()));
        }
        if (dto.getOverride() != null) {
            criterias.add(Criteria.where("override").is(dto.getOverride()));
        }
        return criterias.size() == 0 ? new Criteria() : new Criteria().andOperator(criterias.toArray(new Criteria[0]));
    }

    @Override
    protected void validateRequest(DefaultCategoryDTO dto) throws FabrickPFMMovementsCommonException {
        boolean category = StringUtils.isBlank(dto.getCategory());
        boolean operationType = StringUtils.isBlank(dto.getOperationType());
        boolean direction = StringUtils.isBlank(dto.getDirection());
        boolean operationCode = StringUtils.isBlank(dto.getOperationCode());

        if (category || (operationType && operationCode) || direction) {
            throw new FabrickPFMMovementsCommonException(StatusCode.INVALID_INPUT.getCode(),
                    "Invalid input, category,direction,operationType is null/empty");
        }

        if (dto.getOverride() == null) {
            dto.setOverride(false);
        }
    }

    @Override
    public DefaultCategory validateUpdateRequest(DefaultCategory existingEntity, DefaultCategoryDTO dto) throws FabrickPFMMovementsCommonException {
        if (StringUtils.isBlank(dto.getOperationType()) && StringUtils.isBlank(dto.getOperationCode())) {
            throw new FabrickPFMMovementsCommonException(StatusCode.INVALID_INPUT.getCode(),
                    "Both operationType and operationCode can't be null/empty");
        }
        String category = StringUtils.isBlank(dto.getCategory()) ? existingEntity.getCategory() : dto.getCategory();
        String subCategory = dto.getSubCategory();
        String direction = StringUtils.isBlank(dto.getDirection()) ? existingEntity.getDirection() : dto.getDirection();
        Boolean recurrent = dto.getRecurrent() == null ? existingEntity.getRecurrent() : dto.getRecurrent();
        Boolean override = dto.getOverride() == null ? existingEntity.getOverride() : dto.getOverride();

        DefaultCategory entity = new DefaultCategory();
        entity.setCategory(category);
        entity.setSubCategory(subCategory);
        entity.setDirection(direction);
        entity.setOperationType(dto.getOperationType());
        entity.setOperationCode(dto.getOperationCode());
        entity.setRecurrent(recurrent);
        entity.setOverride(override);
        if (existingEntity.getId() != null && existingEntity.getId() != 0) {
            entity.setId(existingEntity.getId());
        }
        return entity;
    }

    @Override
    public void updateCache(DefaultCategory save) {
        defaultCategoryCacheService.update(save);
    }

    @Override
    public void removeCache(DefaultCategory entity) {
        defaultCategoryCacheService.remove(entity);
    }
}
