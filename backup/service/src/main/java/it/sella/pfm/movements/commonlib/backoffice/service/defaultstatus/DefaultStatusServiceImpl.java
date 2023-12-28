package it.sella.pfm.movements.commonlib.backoffice.service.defaultstatus;

import it.sella.pfm.movements.commonlib.cacheservice.DefaultStatusCacheService;
import it.sella.pfm.movements.commonlib.entity.DefaultStatus;
import it.sella.pfm.movements.commonlib.exception.FabrickPFMMovementsCommonException;
import it.sella.pfm.movements.commonlib.exception.StatusCode;
import it.sella.pfm.movements.commonlib.backoffice.service.BaseService;
import it.sella.pfm.movements.commonlib.mongoservice.BackofficeMongoService;
import it.sella.pfm.movements.commonlib.to.DefaultStatusDTO;
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
public class DefaultStatusServiceImpl extends BaseService<DefaultStatus, DefaultStatusDTO> implements DefaultStatusService {

    private final BackofficeMongoService<DefaultStatus> mongoService;

    @Autowired
    public DefaultStatusServiceImpl(BackofficeMongoService<DefaultStatus> mongoService) {
        super(mongoService);
        this.mongoService = mongoService;
    }

    @Autowired
    DefaultStatusCacheService defaultStatusCacheService;

    @Override
    protected DefaultStatus getEntityObject() {
        return new DefaultStatus();
    }

    @Override
    protected DefaultStatusDTO getDTOObject() {
        return new DefaultStatusDTO();
    }

    @Override
    protected String getServiceName() {
        return this.getClass().getName();
    }

    @Override
    protected Class<DefaultStatus> getClassName() {
        return DefaultStatus.class;
    }

    @Override
    protected DefaultStatus updateSequence(DefaultStatus save) {
        Long id = mongoService.nextVal(SequenceUtil.DEFAULT_STATUS).block();
        save.setId(id);
        return save;
    }

    @Override
    protected String getCollectionName() {
        return SequenceUtil.DEFAULT_STATUS;
    }


    @Override
    protected Criteria getSearchCriteria(DefaultStatusDTO dto) {
        List<Criteria> criterias = new ArrayList<>();

        if (dto.getId() != null && dto.getId() > 0) {
            criterias.add(Criteria.where("_id").is(dto.getId()));
        }
        if (StringUtils.isNotEmpty(dto.getOperationCode())) {
            criterias.add(Criteria.where("operationCode").is(dto.getOperationCode()));
        }
        if (StringUtils.isNotEmpty(dto.getOperationType())) {
            criterias.add(Criteria.where("operationType").is(dto.getOperationType()));
        }
        if (StringUtils.isNotEmpty(dto.getStatus())) {
            criterias.add(Criteria.where("status").is(dto.getStatus()));
        }
        return criterias.size() == 0 ? new Criteria() : new Criteria().andOperator(criterias.toArray(new Criteria[0]));
    }

    @Override
    protected void validateRequest(DefaultStatusDTO defaultStatusDTO) throws FabrickPFMMovementsCommonException {
        String operationCode = defaultStatusDTO.getOperationCode();
        String status = defaultStatusDTO.getStatus();
        String operationType = defaultStatusDTO.getOperationType();

        if (!(StringUtils.isNotBlank(operationCode) || StringUtils.isNotBlank(status)
                || StringUtils.isNotBlank(operationType))) {
            throw new FabrickPFMMovementsCommonException(StatusCode.FAILURE.getCode(),
                    "Invalid input at least one field (operationCode,status,operationType) should not null/empty");
        }
    }

    @Override
    public DefaultStatus validateUpdateRequest(DefaultStatus existingDefaultStatus, DefaultStatusDTO defaultStatusDTO) {
        String operationCode = StringUtils.isNotBlank(defaultStatusDTO.getOperationCode())
                ? defaultStatusDTO.getOperationCode()
                : existingDefaultStatus.getOperationCode();
        String operationType = StringUtils.isNotBlank(defaultStatusDTO.getOperationType())
                ? defaultStatusDTO.getOperationType()
                : existingDefaultStatus.getOperationType();
        String status = StringUtils.isNotBlank(defaultStatusDTO.getStatus()) ? defaultStatusDTO.getStatus()
                : existingDefaultStatus.getStatus();

        existingDefaultStatus.setOperationCode(operationCode);
        existingDefaultStatus.setOperationType(operationType);
        existingDefaultStatus.setStatus(status);

        return existingDefaultStatus;
    }

    @Override
    public void updateCache(DefaultStatus defaultStatus) {
        try {
            defaultStatusCacheService.updateCache(defaultStatus);
        } catch (Exception e) {
            log.error("Exception while updating the cache " + e.getMessage());
        }
    }

    @Override
    public void removeCache(DefaultStatus defaultStatus) {
        try {
            defaultStatusCacheService.removeFromCache(defaultStatus);
        } catch (Exception e) {
            log.error("Exception while removing from cache " + e.getMessage());
        }
    }
}
