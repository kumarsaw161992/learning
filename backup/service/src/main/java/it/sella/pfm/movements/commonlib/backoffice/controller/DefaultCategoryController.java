package it.sella.pfm.movements.commonlib.backoffice.controller;


import io.swagger.annotations.Api;
import it.sella.pfm.movements.commonlib.backoffice.service.defaultcategory.DefaultCategoryService;
import it.sella.pfm.movements.commonlib.entity.DefaultCategory;
import it.sella.pfm.movements.commonlib.to.DefaultCategoryDTO;
import it.sella.pfm.movements.commonlib.utils.MappingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(description = "CURD API for Default Category")
@RestController
@RequestMapping(value = "backoffice/default/category")
public class DefaultCategoryController extends BaseController<DefaultCategory, DefaultCategoryDTO> {

    @Autowired
    DefaultCategoryService defaultCategoryService;

    @Autowired
    public DefaultCategoryController(DefaultCategoryService service) {
        super(service);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    MappingResponse<DefaultCategoryDTO> getPage(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", required = false, defaultValue = "200") Integer limit,
            @RequestParam(value = "sortBy", required = false, defaultValue = "-direction") String sortBy,
            DefaultCategoryDTO defaultCategory) {
        return defaultCategoryService.getPage(offset, limit, sortBy, defaultCategory);
    }

}
