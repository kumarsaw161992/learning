package it.sella.pfm.movements.commonlib.backoffice.controller;


import io.swagger.annotations.Api;
import it.sella.pfm.movements.commonlib.backoffice.service.causaleinterna.CausaleInternaService;
import it.sella.pfm.movements.commonlib.entity.CausaleInterna;
import it.sella.pfm.movements.commonlib.to.CausaleInternaDTO;
import it.sella.pfm.movements.commonlib.utils.MappingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(description = "CURD API for Causale Interna")
@RestController
@RequestMapping(value = "backoffice/causale/interna")
public class CausaleInternaController extends BaseController<CausaleInterna, CausaleInternaDTO> {

    @Autowired
    CausaleInternaService causaleInternaService;

    @Autowired
    public CausaleInternaController(CausaleInternaService service) {
        super(service);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    MappingResponse<CausaleInternaDTO> getPage(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                               @RequestParam(value = "limit", required = false, defaultValue = "200") Integer limit,
                                               @RequestParam(value = "sortBy", required = false, defaultValue = "-causaleAbbreviation") String sortBy,
                                               CausaleInternaDTO causaleInternaDTO) {
        return causaleInternaService.getPage(offset, limit, sortBy, causaleInternaDTO);
    }
}
