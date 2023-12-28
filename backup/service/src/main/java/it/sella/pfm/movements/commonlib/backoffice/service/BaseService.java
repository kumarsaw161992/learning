package it.sella.pfm.movements.commonlib.backoffice.service;

import it.sella.pfm.movements.commonlib.entity.BaseEntity;
import it.sella.pfm.movements.commonlib.exception.FabrickPFMMovementsCommonException;
import it.sella.pfm.movements.commonlib.exception.StatusCode;
import it.sella.pfm.movements.commonlib.mongoservice.BackofficeMongoService;
import it.sella.pfm.movements.commonlib.to.BaseDTO;
import it.sella.pfm.movements.commonlib.utils.*;
import lombok.CustomLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;

@CustomLog
public abstract class BaseService<E extends BaseEntity, DTO extends BaseDTO> implements IBaseService<E, DTO> {

    private final BackofficeMongoService<E> mongoService;

    public BaseService(BackofficeMongoService<E> mongoService) {
        super();
        this.mongoService = mongoService;
    }

    protected abstract void validateRequest(DTO dto) throws FabrickPFMMovementsCommonException;

    protected abstract E getEntityObject();

    protected abstract DTO getDTOObject();

    protected abstract String getServiceName();

    protected abstract Class<E> getClassName();

    protected abstract String getCollectionName();

    protected abstract E updateSequence(E save);

    protected abstract Criteria getSearchCriteria(DTO dto);

    @Override
    public MappingResponse<DTO> save(DTO dto) {
        MappingResponse mappingResponse = new MappingResponse<DTO>();
        try {
            validateRequest(dto);
            checkIfAlreadyExist(dto);
            E save = RequestMapper.copyPorps(dto, getEntityObject());
            save = updateSequence(save);
            save = mongoService.save(save);
            updateCache(save);
            DTO responseDTO = RequestMapper.copyPorps(save, getDTOObject());
            mappingResponse.setStatus(StatusCode.SUCCESS.getDesc());
            mappingResponse.setMapping(responseDTO);
        } catch (Exception e) {
            log.error(getServiceName() + ":Exception inside save :", e);
            mappingResponse.setStatus(StatusCode.FAILURE.getDesc());
            mappingResponse.setError(e.getMessage());
        }
        return mappingResponse;
    }

    @Override
    public MappingResponse<DTO> update(DTO dto) {
        MappingResponse mappingResponse = new MappingResponse<DTO>();
        Long id = dto.getId();
        try {
            if (id == null || id == 0) {
                throw new FabrickPFMMovementsCommonException(StatusCode.FAILURE.getCode(), "Invalid input id is null/zero");
            }
            validateRequest(dto);
            E existingEntity = mongoService.findById(id, getClassName());
            if (existingEntity != null) {
                E update = validateUpdateRequest(existingEntity, dto);
                update = mongoService.save(update);
                updateCache(update);
                DTO responseDTO = RequestMapper.copyPorps(update, getDTOObject());
                mappingResponse.setStatus(StatusCode.SUCCESS.getDesc());
                mappingResponse.setMapping(responseDTO);
            } else {
                throw new FabrickPFMMovementsCommonException(StatusCode.FAILURE.getCode(), "Unable to fetch existing record of ID:" + id);
            }
        } catch (Exception e) {
            log.error(getServiceName() + ":Exception inside update :", e);
            mappingResponse.setStatus(StatusCode.FAILURE.getDesc());
            mappingResponse.setError(e.getMessage());
        }
        return mappingResponse;
    }

    @Override
    public MappingResponse<DTO> findAll() {
        MappingResponse mappingResponse = new MappingResponse<DTO>();
        try {
            List<E> allRecord = mongoService.findAll(getClassName());
            mappingResponse.setStatus(StatusCode.SUCCESS.getDesc());
            mappingResponse.setMappingList(getDTOList(allRecord));
        } catch (Exception e) {
            log.error(getServiceName() + ":Exception inside findAll :", e);
            mappingResponse.setStatus(StatusCode.FAILURE.getDesc());
            mappingResponse.setError(e.getMessage());
        }
        return mappingResponse;
    }

    @Override
    public MappingResponse<DTO> getPage(Integer offset, Integer limit, String sortBy, DTO dto) {
        MappingResponse mappingResponse = new MappingResponse<DTO>();
        PageDetails<E> pageDetails = new PageDetails<>();
        Page<E> entityPage = null;
        try {
            RequestMapper.validateRequest(offset, limit);
            List<Sort.Order> orders = RequestMapper.getOrders(sortBy);
            entityPage = mongoService.getPage(getSearchCriteria(dto),
                    new OffsetBasedPageRequest(offset, limit, Sort.by(orders)),
                    getClassName(), getCollectionName());
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
            log.error(getServiceName() + ":Exception inside getPage :", e);
            mappingResponse.setStatus(StatusCode.FAILURE.getDesc());
            mappingResponse.setError(e.getMessage());
        }
        return mappingResponse;
    }

    @Override
    public MappingResponse<DTO> get(Long id) {
        MappingResponse<DTO> mappingResponse = new MappingResponse<>();
        try {
            E entity = mongoService.findById(id, getClassName());
            if (entity != null) {
                mappingResponse.setStatus(StatusCode.SUCCESS.getDesc());
                mappingResponse.setMapping(RequestMapper.copyPorps(entity, getDTOObject()));
            } else {
                throw new FabrickPFMMovementsCommonException(StatusCode.DATA_NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(getServiceName() + ":Exception inside get by Id :" + id + " ", e);
            mappingResponse.setStatus(StatusCode.FAILURE.getDesc());
            mappingResponse.setError(e.getMessage());
        }
        return mappingResponse;
    }

    @Override
    public MappingResponse<DTO> delete(Long id) {
        MappingResponse<DTO> mappingResponse = new MappingResponse<>();
        try {
            E entity = mongoService.findById(id, getClassName());
            if (entity != null) {
                mongoService.deleteById(entity.getId(), getClassName());
                removeCache(entity);
                mappingResponse.setStatus(StatusCode.SUCCESS.getDesc());
            } else {
                throw new FabrickPFMMovementsCommonException(StatusCode.DATA_NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(getServiceName() + ":Exception inside delete :", e);
            mappingResponse.setStatus(StatusCode.FAILURE.getDesc());
            mappingResponse.setError(e.getMessage());
        }
        return mappingResponse;
    }

    private List<DTO> getDTOList(List<E> allRecord) {
        List<DTO> dtoList = new ArrayList<>();
        if (allRecord != null && !allRecord.isEmpty()) {
            for (E record : allRecord) {
                dtoList.add(RequestMapper.copyPorps(record, getDTOObject()));
            }
        }
        return dtoList;
    }

    //Default logic, must be overriden in service impl if requred defferent logic
    public E validateUpdateRequest(E entity, DTO dto) throws FabrickPFMMovementsCommonException {
        E update = RequestMapper.copyPorps(dto, getEntityObject());
        update.setId(entity.getId());
        return update;
    }

    //Default logic, must be overriden in service impl if requred defferent logic
    public void checkIfAlreadyExist(DTO dto) throws FabrickPFMMovementsCommonException {
    }

    //Default logic, must be overriden in service impl if requred defferent logic
    public void updateCache(E save) {
    }

    //Default logic, must be overriden in service impl if requred defferent logic
    public void removeCache(E entity) {
    }
}
