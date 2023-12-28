package it.sella.pfm.movements.commonlib.backoffice.service;

import it.sella.pfm.movements.commonlib.entity.BaseEntity;
import it.sella.pfm.movements.commonlib.to.BaseDTO;
import it.sella.pfm.movements.commonlib.utils.MappingResponse;

public interface IBaseService<E extends BaseEntity, DTO extends BaseDTO> {

    MappingResponse<DTO> save(DTO dto);

    MappingResponse<DTO> update(DTO dto);

    MappingResponse<DTO> findAll();

    MappingResponse<DTO> get(Long id);

    MappingResponse<DTO> delete(Long id);

    MappingResponse getPage(Integer offset, Integer limit, String sortBy, DTO dto);
}
