package it.sella.pfm.movements.commonlib.backoffice.controller;


import it.sella.pfm.movements.commonlib.entity.BaseEntity;
import it.sella.pfm.movements.commonlib.to.BaseDTO;
import it.sella.pfm.movements.commonlib.utils.MappingResponse;
import it.sella.pfm.movements.commonlib.backoffice.service.IBaseService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

public class BaseController<E extends BaseEntity, DTO extends BaseDTO> {

    private final IBaseService<E, DTO> baseService;

    public BaseController(IBaseService<E, DTO> baseService) {
        this.baseService = baseService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public MappingResponse<DTO> save(@RequestBody DTO dto) {
        return baseService.save(dto);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public MappingResponse<DTO> update(@RequestBody DTO dto) {
        return baseService.update(dto);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MappingResponse<DTO> delete(@PathVariable Long id) {
        return baseService.delete(id);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MappingResponse<DTO> findById(@PathVariable Long id) {
        return baseService.get(id);
    }


}
