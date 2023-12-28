package it.sella.pfm.movements.commonlib.backoffice.service.instrumenttype;

import it.sella.pfm.movements.commonlib.cacheservice.InstrumentTypeCacheService;
import it.sella.pfm.movements.commonlib.entity.InstrumentType;
import it.sella.pfm.movements.commonlib.exception.FabrickPFMMovementsCommonException;
import it.sella.pfm.movements.commonlib.exception.StatusCode;
import it.sella.pfm.movements.commonlib.mongoservice.BackofficeMongoService;
import it.sella.pfm.movements.commonlib.to.InstrumentTypeDTO;
import it.sella.pfm.movements.commonlib.utils.*;
import lombok.CustomLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@CustomLog
@Service
public class InstrumentTypeServiceImpl {

    @Autowired
    private InstrumentTypeCacheService instrumentTypeCacheService;

    @Autowired
    private BackofficeMongoService<InstrumentType> mongoService;

    public MappingResponse<InstrumentTypeDTO> save(InstrumentTypeDTO dto) {
        MappingResponse mappingResponse = new MappingResponse<InstrumentTypeDTO>();
        try {
            validateRequest(dto);
            InstrumentType save = RequestMapper.copyPorps(dto, new InstrumentType());
            save = mongoService.save(save);
            updateCache(save);
            InstrumentTypeDTO responseDTO = RequestMapper.copyPorps(save, new InstrumentTypeDTO());
            mappingResponse.setStatus(StatusCode.SUCCESS.getDesc());
            mappingResponse.setMapping(responseDTO);
        } catch (Exception e) {
            log.error("Exception inside save :", e);
            mappingResponse.setStatus(StatusCode.FAILURE.getDesc());
            mappingResponse.setError(e.getMessage());
        }
        return mappingResponse;
    }

    public MappingResponse<InstrumentTypeDTO> update(InstrumentTypeDTO dto) {
        MappingResponse mappingResponse = new MappingResponse<InstrumentTypeDTO>();
        String id = dto.getOperationType();
        try {
            if (StringUtils.isEmpty(id)) {
                throw new FabrickPFMMovementsCommonException(StatusCode.FAILURE.getCode(), "Invalid input operationType is null/Empty");
            }
            validateRequest(dto);
            InstrumentType existingEntity = mongoService.findById(id, InstrumentType.class);
            if (existingEntity != null) {
                InstrumentType update = validateUpdateRequest(existingEntity, dto);
                update = mongoService.save(update);
                updateCache(update);
                InstrumentTypeDTO responseDTO = RequestMapper.copyPorps(update, new InstrumentTypeDTO());
                mappingResponse.setStatus(StatusCode.SUCCESS.getDesc());
                mappingResponse.setMapping(responseDTO);
            } else {
                throw new FabrickPFMMovementsCommonException(StatusCode.FAILURE.getCode(), "Unable to fetch existing record of ID:" + id);
            }
        } catch (Exception e) {
            log.error("Exception inside update :", e);
            mappingResponse.setStatus(StatusCode.FAILURE.getDesc());
            mappingResponse.setError(e.getMessage());
        }
        return mappingResponse;
    }

    public MappingResponse<InstrumentTypeDTO> findAll() {
        MappingResponse mappingResponse = new MappingResponse<InstrumentTypeDTO>();
        try {
            List<InstrumentType> allRecord = mongoService.findAll(InstrumentType.class);
            mappingResponse.setStatus(StatusCode.SUCCESS.getDesc());
            mappingResponse.setMappingList(getDTOList(allRecord));
        } catch (Exception e) {
            log.error("Exception inside findAll :", e);
            mappingResponse.setStatus(StatusCode.FAILURE.getDesc());
            mappingResponse.setError(e.getMessage());
        }
        return mappingResponse;
    }

    public MappingResponse<InstrumentTypeDTO> getPage(Integer offset, Integer limit, String sortBy, InstrumentTypeDTO dto) {
        MappingResponse mappingResponse = new MappingResponse<InstrumentTypeDTO>();
        PageDetails<InstrumentType> pageDetails = new PageDetails<>();
        Page<InstrumentType> entityPage = null;
        try {
            RequestMapper.validateRequest(offset, limit);
            List<Sort.Order> orders = RequestMapper.getOrders(sortBy);
            entityPage = mongoService.getPage(getSearchCriteria(dto),
                    new OffsetBasedPageRequest(offset, limit, Sort.by(orders)),
                    InstrumentType.class, SequenceUtil.INSTRUMENT_TYPE);
            if (entityPage != null) {
                mappingResponse.setStatus(StatusCode.SUCCESS.getDesc());
                mappingResponse.setPagination(pageDetails.getPagination(entityPage));
                mappingResponse.getPagination().setOffset(offset);
                mappingResponse.setSorting(pageDetails.getSorting(entityPage));
                mappingResponse.setMappingList(getDTOList(entityPage.getContent()));
            } else {
                throw new FabrickPFMMovementsCommonException(StatusCode.DATA_NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Exception inside getPage :", e);
            mappingResponse.setStatus(StatusCode.FAILURE.getDesc());
            mappingResponse.setError(e.getMessage());
        }
        return mappingResponse;
    }

    public MappingResponse<InstrumentTypeDTO> get(String id) {
        MappingResponse<InstrumentTypeDTO> mappingResponse = new MappingResponse<>();
        try {
            InstrumentType entity = mongoService.findById(id, InstrumentType.class);
            if (entity != null) {
                mappingResponse.setStatus(StatusCode.SUCCESS.getDesc());
                mappingResponse.setMapping(RequestMapper.copyPorps(entity, new InstrumentTypeDTO()));
            } else {
                throw new FabrickPFMMovementsCommonException(StatusCode.DATA_NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Exception inside get by Id :" + id + " ", e);
            mappingResponse.setStatus(StatusCode.FAILURE.getDesc());
            mappingResponse.setError(e.getMessage());
        }
        return mappingResponse;
    }

    public MappingResponse<InstrumentTypeDTO> delete(String id) {
        MappingResponse<InstrumentTypeDTO> mappingResponse = new MappingResponse<>();
        try {
            InstrumentType entity = mongoService.findById(id, InstrumentType.class);
            if (entity != null) {
                mongoService.deleteById(entity.getOperationType(), InstrumentType.class);
                removeCache(entity);
                mappingResponse.setStatus(StatusCode.SUCCESS.getDesc());
            } else {
                throw new FabrickPFMMovementsCommonException(StatusCode.DATA_NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Exception inside delete :", e);
            mappingResponse.setStatus(StatusCode.FAILURE.getDesc());
            mappingResponse.setError(e.getMessage());
        }
        return mappingResponse;
    }

    private List<InstrumentTypeDTO> getDTOList(List<InstrumentType> allRecord) {
        List<InstrumentTypeDTO> dtoList = new ArrayList<>();
        if (allRecord != null && !allRecord.isEmpty()) {
            for (InstrumentType record : allRecord) {
                dtoList.add(RequestMapper.copyPorps(record, new InstrumentTypeDTO()));
            }
        }
        return dtoList;
    }


    private void validateRequest(InstrumentTypeDTO dto) throws FabrickPFMMovementsCommonException {
        boolean operationType = StringUtils.isEmpty(dto.getOperationType());
        boolean operation = StringUtils.isEmpty(dto.getOperation());
        boolean instrumentType = StringUtils.isEmpty(dto.getInstrumentType());
        if (operationType || operation || instrumentType) {
            throw new FabrickPFMMovementsCommonException(StatusCode.INVALID_INPUT.getCode(),
                    "Invalid input, operationType,operation,instrumentType is null/empty");
        }
    }

    private InstrumentType validateUpdateRequest(InstrumentType existingEntity, InstrumentTypeDTO dto) throws FabrickPFMMovementsCommonException {
        if (StringUtils.isBlank(dto.getOperationType())) {
            throw new FabrickPFMMovementsCommonException(StatusCode.INVALID_INPUT.getCode(),
                    "operationType can't be null/empty");
        }
        String operation = StringUtils.isEmpty(dto.getOperation()) ? existingEntity.getOperation() : dto.getOperation();
        String instrumentType = StringUtils.isEmpty(dto.getInstrumentType()) ? existingEntity.getInstrumentType() : dto.getInstrumentType();
        existingEntity.setOperation(operation);
        existingEntity.setInstrumentType(instrumentType);
        return existingEntity;
    }

    private void updateCache(InstrumentType instrumentType) {
        try {
            instrumentTypeCacheService.updateCache(instrumentType);
        } catch (Exception e) {
            log.error("Exception while updating the cache " + e.getMessage());
        }
    }

    private void removeCache(InstrumentType instrumentType) {
        try {
            instrumentTypeCacheService.removeFromCache(instrumentType);
        } catch (Exception e) {
            log.error("Exception while removing from cache " + e.getMessage());
        }
    }

    private Criteria getSearchCriteria(InstrumentTypeDTO dto) {
        List<Criteria> criterias = new ArrayList<>();

        if (StringUtils.isNotEmpty(dto.getOperationType())) {
            criterias.add(Criteria.where("_id").is(dto.getOperationType()));
        }
        if (StringUtils.isNotEmpty(dto.getOperation())) {
            criterias.add(Criteria.where("operation").is(dto.getOperation()));
        }
        if (StringUtils.isNotEmpty(dto.getInstrumentType())) {
            criterias.add(Criteria.where("instrumentType").is(dto.getInstrumentType()));
        }

        return criterias.size() == 0 ? new Criteria() : new Criteria().andOperator(criterias.toArray(new Criteria[0]));
    }
}
