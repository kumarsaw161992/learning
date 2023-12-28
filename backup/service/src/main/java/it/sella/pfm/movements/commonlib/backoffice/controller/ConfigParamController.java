package it.sella.pfm.movements.commonlib.backoffice.controller;

import io.swagger.annotations.Api;
import it.sella.pfm.movements.commonlib.backoffice.service.configparam.ConfigParamService;
import it.sella.pfm.movements.commonlib.entity.ConfigParam;
import it.sella.pfm.movements.commonlib.to.ConfigParamDTO;
import it.sella.pfm.movements.commonlib.utils.MappingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(description = "CURD API for Config Param")
@RestController
@RequestMapping(value = "backoffice/config/param")
public class ConfigParamController extends BaseController<ConfigParam, ConfigParamDTO> {

    @Autowired
    ConfigParamService configParamService;

    @Autowired
    public ConfigParamController(ConfigParamService service) {
        super(service);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    MappingResponse getPage(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                            @RequestParam(value = "limit", required = false, defaultValue = "200") Integer limit,
                            @RequestParam(value = "sortBy", required = false, defaultValue = "-name") String sortBy,
                            ConfigParamDTO configParam) {
        return configParamService.getPage(offset, limit, sortBy, configParam);
    }

}
