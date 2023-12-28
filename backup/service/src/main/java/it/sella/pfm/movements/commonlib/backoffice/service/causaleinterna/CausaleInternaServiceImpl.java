package it.sella.pfm.movements.commonlib.backoffice.service.causaleinterna;

import it.sella.pfm.movements.commonlib.cacheservice.CausaleInternaCacheService;
import it.sella.pfm.movements.commonlib.entity.CausaleInterna;
import it.sella.pfm.movements.commonlib.exception.FabrickPFMMovementsCommonException;
import it.sella.pfm.movements.commonlib.exception.StatusCode;
import it.sella.pfm.movements.commonlib.backoffice.service.BaseService;
import it.sella.pfm.movements.commonlib.mongoservice.BackofficeMongoService;
import it.sella.pfm.movements.commonlib.to.CausaleInternaDTO;
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
public class CausaleInternaServiceImpl extends BaseService<CausaleInterna, CausaleInternaDTO> implements CausaleInternaService {

    private final BackofficeMongoService<CausaleInterna> mongoService;

    @Autowired
    public CausaleInternaServiceImpl(BackofficeMongoService<CausaleInterna> mongoService) {
        super(mongoService);
        this.mongoService = mongoService;
    }

    @Autowired
    CausaleInternaCacheService causaleInternaCacheService;

    @Override
    protected CausaleInterna getEntityObject() {
        return new CausaleInterna();
    }

    @Override
    protected CausaleInternaDTO getDTOObject() {
        return new CausaleInternaDTO();
    }

    @Override
    protected String getServiceName() {
        return this.getClass().getName();
    }

    @Override
    protected Class<CausaleInterna> getClassName() {
        return CausaleInterna.class;
    }

    @Override
    protected CausaleInterna updateSequence(CausaleInterna save) {
        Long id = mongoService.nextVal(SequenceUtil.CAUSALE_INTERNA).block();
        save.setId(id);
        return save;
    }

    @Override
    protected String getCollectionName() {
        return SequenceUtil.CAUSALE_INTERNA;
    }

    @Override
    protected Criteria getSearchCriteria(CausaleInternaDTO dto) {
        List<Criteria> criterias = new ArrayList<>();

        if (dto.getId() != null && dto.getId() > 0) {
            criterias.add(Criteria.where("_id").is(dto.getId()));
        }
        if (StringUtils.isNotEmpty(dto.getCausaleAbbreviation())) {
            criterias.add(Criteria.where("causaleAbbreviation").is(dto.getCausaleAbbreviation()));
        }
        if (StringUtils.isNotEmpty(dto.getCausaleHost())) {
            criterias.add(Criteria.where("causaleHost").is(dto.getCausaleHost()));
        }
        if (StringUtils.isNotEmpty(dto.getCausaleInternaCode())) {
            criterias.add(Criteria.where("causaleInternaCode").is(dto.getCausaleInternaCode()));
        }
        if (StringUtils.isNotEmpty(dto.getCausaleInstrumentType())) {
            criterias.add(Criteria.where("causaleInstrumentType").is(dto.getCausaleInstrumentType()));
        }
        if (StringUtils.isNotEmpty(dto.getCausaleStructured())) {
            criterias.add(Criteria.where("causaleStructured").is(dto.getCausaleStructured()));
        }
        if (dto.getOverride() != null){
            criterias.add(Criteria.where("override").is(dto.getOverride()));
        }

        return criterias.size() == 0 ? new Criteria() : new Criteria().andOperator(criterias.toArray(new Criteria[0]));
    }

    @Override
    protected void validateRequest(CausaleInternaDTO causaleInternaDTO) throws FabrickPFMMovementsCommonException {
        String causaleAbbr = causaleInternaDTO.getCausaleAbbreviation();
        String causaleHost = causaleInternaDTO.getCausaleHost();
        String causaleInternaCode = causaleInternaDTO.getCausaleInternaCode();
        if ((StringUtils.isBlank(causaleInternaCode)
                && (StringUtils.isBlank(causaleAbbr) || StringUtils.isBlank(causaleHost)))
                || StringUtils.isBlank(causaleInternaDTO.getCausaleInstrumentType())) {
            throw new FabrickPFMMovementsCommonException(StatusCode.FAILURE.getCode(),
                    "Invalid input causaleInternaCode/causaleHost/causaleAbbr/InstrumentType is null/empty");
        }
    }

    @Override
    public void checkIfAlreadyExist(CausaleInternaDTO causaleInternaDTO) throws FabrickPFMMovementsCommonException {
        CausaleInterna causaleInterna = null;
        if (StringUtils.isNotBlank(causaleInternaDTO.getCausaleInternaCode())) {
            causaleInterna = mongoService
                    .findByCausaleInternaCode(causaleInternaDTO.getCausaleInternaCode(), getClassName());
        } else {
            causaleInterna = mongoService
                    .findByCausaleAbbreviationAndCausaleHostAndCausaleInternaCodeIsNull(
                            causaleInternaDTO.getCausaleAbbreviation(), causaleInternaDTO.getCausaleHost(), getClassName());
        }
        if (causaleInterna != null
                && !(causaleInternaDTO.getId() != null && causaleInternaDTO.getId() == causaleInterna.getId())) {
            throw new FabrickPFMMovementsCommonException(StatusCode.FAILURE.getCode(),
                    "Mapping already exists for the Causale Abbr/Causale host or for Causale code");
        }
    }

    @Override
    public void updateCache(CausaleInterna causaleInterna) {
        try {
            causaleInternaCacheService.updateCache(causaleInterna);
            causaleInternaCacheService.removeDefaultMappingFromCache(causaleInterna);
        } catch (Exception e) {
            log.error("Exception while updating the cache " + e.getMessage());
        }
    }

    @Override
    public void removeCache(CausaleInterna causaleInterna) {
        try {
            causaleInternaCacheService.removeFromCache(causaleInterna);
        } catch (Exception e) {
            log.error("Exception while removing from cache " + e.getMessage());
        }
    }
}
