package it.sella.pfm.movements.commonlib.backoffice.controller;

import io.swagger.annotations.Api;
import it.sella.pfm.movements.commonlib.backoffice.service.instrumenttype.InstrumentTypeServiceImpl;
import it.sella.pfm.movements.commonlib.to.InstrumentTypeDTO;
import it.sella.pfm.movements.commonlib.utils.MappingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(description = "CURD API for Instrument Type")
@RestController
@RequestMapping(value = "backoffice/instrument/type")
public class InstrumentTypeController {

    @Autowired
    InstrumentTypeServiceImpl instrumentTypeService;


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public MappingResponse<InstrumentTypeDTO> save(@RequestBody InstrumentTypeDTO dto) {
        return instrumentTypeService.save(dto);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public MappingResponse<InstrumentTypeDTO> update(@RequestBody InstrumentTypeDTO dto) {
        return instrumentTypeService.update(dto);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MappingResponse<InstrumentTypeDTO> delete(@PathVariable String id) {
        return instrumentTypeService.delete(id);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MappingResponse<InstrumentTypeDTO> findById(@PathVariable String id) {
        return instrumentTypeService.get(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    MappingResponse<InstrumentTypeDTO> getPage(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", required = false, defaultValue = "100") Integer limit,
            @RequestParam(value = "sortBy", required = false, defaultValue = "-operationType") String sortBy,
            InstrumentTypeDTO instrumentType) {
        return instrumentTypeService.getPage(offset, limit, sortBy, instrumentType);
    }

}
