package it.sella.pfm.movements.commonlib.backoffice.service.configparam;

import it.sella.pfm.movements.commonlib.cacheservice.ConfigParamCacheService;
import it.sella.pfm.movements.commonlib.entity.ConfigParam;
import it.sella.pfm.movements.commonlib.exception.FabrickPFMMovementsCommonException;
import it.sella.pfm.movements.commonlib.exception.StatusCode;
import it.sella.pfm.movements.commonlib.backoffice.service.BaseService;
import it.sella.pfm.movements.commonlib.mongoservice.BackofficeMongoService;
import it.sella.pfm.movements.commonlib.to.ConfigParamDTO;
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
public class ConfigParamServiceImpl extends BaseService<ConfigParam, ConfigParamDTO> implements ConfigParamService {

    private final BackofficeMongoService<ConfigParam> mongoService;

    @Autowired
    public ConfigParamServiceImpl(BackofficeMongoService<ConfigParam> mongoService) {
        super(mongoService);
        this.mongoService = mongoService;
    }

    @Autowired
    ConfigParamCacheService configParamCacheService;

    @Override
    protected ConfigParam getEntityObject() {
        return new ConfigParam();
    }

    @Override
    protected ConfigParamDTO getDTOObject() {
        return new ConfigParamDTO();
    }

    @Override
    protected String getServiceName() {
        return this.getClass().getName();
    }

    @Override
    protected Class<ConfigParam> getClassName() {
        return ConfigParam.class;
    }

    @Override
    protected ConfigParam updateSequence(ConfigParam save) {
        Long id = mongoService.nextVal(SequenceUtil.CONFIG_PARAM).block();
        save.setId(id);
        return save;
    }

    @Override
    protected String getCollectionName() {
        return SequenceUtil.CONFIG_PARAM;
    }

    @Override
    protected Criteria getSearchCriteria(ConfigParamDTO dto) {
        List<Criteria> criterias = new ArrayList<>();

        if (dto.getId() != null && dto.getId() > 0) {
            criterias.add(Criteria.where("_id").is(dto.getId()));
        }
        if (StringUtils.isNotEmpty(dto.getName())) {
            criterias.add(Criteria.where("name").is(dto.getName()));
        }
        if (StringUtils.isNotEmpty(dto.getValue())) {
            criterias.add(Criteria.where("value").is(dto.getValue()));
        }
        return criterias.size() == 0 ? new Criteria() : new Criteria().andOperator(criterias.toArray(new Criteria[0]));
    }

    @Override
    protected void validateRequest(ConfigParamDTO configParam) throws FabrickPFMMovementsCommonException {
        String name = configParam.getName();
        String value = configParam.getValue();
        if (StringUtils.isBlank(name) || StringUtils.isBlank(value)) {
            throw new FabrickPFMMovementsCommonException(StatusCode.INVALID_INPUT.getCode(),
                    "Invalid Input name and value is null/empty");
        }
    }

    @Override
    public void updateCache(ConfigParam configParam) {
        try {
            configParamCacheService.updateCache(configParam);
        } catch (Exception e) {
            log.error("Exception while updating the cache " + e.getMessage());
        }
    }

    @Override
    public void removeCache(ConfigParam configParam) {
        try {
            configParamCacheService.removeFromCache(configParam);
        } catch (Exception e) {
            log.error("Exception while removing from cache " + e.getMessage());
        }
    }
}
