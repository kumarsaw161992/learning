package it.sella.pfm.movements.commonlib.backoffice.controller;

import io.swagger.annotations.Api;
import it.sella.pfm.movements.commonlib.backoffice.service.defaultstatus.DefaultStatusService;
import it.sella.pfm.movements.commonlib.entity.DefaultStatus;
import it.sella.pfm.movements.commonlib.to.DefaultStatusDTO;
import it.sella.pfm.movements.commonlib.utils.MappingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(description = "CURD API for Default Status")
@RestController
@RequestMapping(value = "backoffice/default/status")
public class DefaultStatusController extends BaseController<DefaultStatus, DefaultStatusDTO> {

    @Autowired
    DefaultStatusService defaultStatusService;

    @Autowired
    public DefaultStatusController(DefaultStatusService service) {
        super(service);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    MappingResponse<DefaultStatusDTO> getPage(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                              @RequestParam(value = "limit", required = false, defaultValue = "200") Integer limit,
                                              @RequestParam(value = "sortBy", required = false, defaultValue = "-operationCode") String sortBy,
                                              DefaultStatusDTO defaultStatusDTO) {
        return defaultStatusService.getPage(offset, limit, sortBy, defaultStatusDTO);
    }

}
