package it.sella.pfm.movements.commonlib.backoffice.service.staticcausale;

import it.sella.pfm.movements.commonlib.backoffice.service.BaseService;
import it.sella.pfm.movements.commonlib.cacheservice.StaticCausaleCacheService;
import it.sella.pfm.movements.commonlib.entity.StaticCausale;
import it.sella.pfm.movements.commonlib.exception.FabrickPFMMovementsCommonException;
import it.sella.pfm.movements.commonlib.exception.StatusCode;
import it.sella.pfm.movements.commonlib.mongoservice.BackofficeMongoService;
import it.sella.pfm.movements.commonlib.to.StaticCausaleDTO;
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
public class StaticCausaleServiceImpl extends BaseService<StaticCausale, StaticCausaleDTO> implements StaticCausaleService {

    private final BackofficeMongoService<StaticCausale> mongoService;

    @Autowired
    public StaticCausaleServiceImpl(BackofficeMongoService<StaticCausale> mongoService) {
        super(mongoService);
        this.mongoService = mongoService;
    }

    @Autowired
    StaticCausaleCacheService staticCausaleCacheService;

    @Override
    protected StaticCausale getEntityObject() {
        return new StaticCausale();
    }

    @Override
    protected StaticCausaleDTO getDTOObject() {
        return new StaticCausaleDTO();
    }

    @Override
    protected String getServiceName() {
        return this.getClass().getName();
    }

    @Override
    protected Class<StaticCausale> getClassName() {
        return StaticCausale.class;
    }

    @Override
    protected StaticCausale updateSequence(StaticCausale save) {
        Long id = mongoService.nextVal(SequenceUtil.STATIC_CAUSALE).block();
        save.setId(id);
        return save;
    }

    @Override
    protected String getCollectionName() {
        return SequenceUtil.STATIC_CAUSALE;
    }

    @Override
    protected Criteria getSearchCriteria(StaticCausaleDTO dto) {
        List<Criteria> criterias = new ArrayList<>();

        if (dto.getId() != null && dto.getId() > 0) {
            criterias.add(Criteria.where("_id").is(dto.getId()));
        }
        if (StringUtils.isNotEmpty(dto.getCausale())) {
            criterias.add(Criteria.where("causale").is(dto.getCausale()));
        }
        if (StringUtils.isNotEmpty(dto.getCausaleToBe())) {
            criterias.add(Criteria.where("causaleToBe").is(dto.getCausaleToBe()));
        }
        if (dto.getIsDisabled() != null) {
            criterias.add(Criteria.where("isDisabled").is(dto.getIsDisabled()));
        }
        return criterias.size() == 0 ? new Criteria() : new Criteria().andOperator(criterias.toArray(new Criteria[0]));
    }

    @Override
    protected void validateRequest(StaticCausaleDTO dto) throws FabrickPFMMovementsCommonException {
        String causaleAsIs = dto.getCausale();
        String causaleToBe = dto.getCausaleToBe();
        if (StringUtils.isEmpty(causaleAsIs) || StringUtils.isEmpty(causaleToBe)) {
            throw new FabrickPFMMovementsCommonException(StatusCode.FAILURE.getCode(),
                    "Invalid input causale/causaleToBe is null/empty");
        }
        if (dto.getIsDisabled() == null) {
            dto.setIsDisabled(false);
        }
    }

    @Override
    public StaticCausale validateUpdateRequest(StaticCausale existingStaticCausale, StaticCausaleDTO staticCausaleDTO) {

        if (StringUtils.isNotEmpty(staticCausaleDTO.getCausale())) {
            existingStaticCausale.setCausale(staticCausaleDTO.getCausale());
        }

        if (StringUtils.isNotEmpty(staticCausaleDTO.getCausaleToBe())) {
            existingStaticCausale.setCausaleToBe(staticCausaleDTO.getCausaleToBe());
        }

        Boolean isDisable = staticCausaleDTO.getIsDisabled();
        if (isDisable != null) {
            existingStaticCausale.setIsDisabled(isDisable);
        }

        return existingStaticCausale;
    }

    @Override
    public void updateCache(StaticCausale staticCausale) {
        try {
            staticCausaleCacheService.updateCache(staticCausale);
        } catch (Exception e) {
            log.error("Exception while updating the cache " + e.getMessage());
        }
    }

    @Override
    public void removeCache(StaticCausale staticCausale) {
        try {
            staticCausaleCacheService.removeFromCache(staticCausale);
        } catch (Exception e) {
            log.error("Exception while removing from cache " + e.getMessage());
        }
    }
}
