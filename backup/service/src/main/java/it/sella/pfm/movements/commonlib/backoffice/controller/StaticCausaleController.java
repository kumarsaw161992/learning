package it.sella.pfm.movements.commonlib.backoffice.controller;

import io.swagger.annotations.Api;
import it.sella.pfm.movements.commonlib.backoffice.service.staticcausale.StaticCausaleService;
import it.sella.pfm.movements.commonlib.entity.StaticCausale;
import it.sella.pfm.movements.commonlib.to.StaticCausaleDTO;
import it.sella.pfm.movements.commonlib.utils.MappingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(description = "CURD API for Static Causale")
@RestController
@RequestMapping(value = "backoffice/static/causale")
public class StaticCausaleController extends BaseController<StaticCausale, StaticCausaleDTO> {

    @Autowired
    StaticCausaleService staticCausaleService;

    @Autowired
    public StaticCausaleController(StaticCausaleService service) {
        super(service);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    MappingResponse getPage(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                            @RequestParam(value = "limit", required = false, defaultValue = "200") Integer limit,
                            @RequestParam(value = "sortBy", required = false, defaultValue = "-causale") String sortBy,
                            StaticCausaleDTO staticCausaleDTO) {
        return staticCausaleService.getPage(offset, limit, sortBy, staticCausaleDTO);
    }

}
