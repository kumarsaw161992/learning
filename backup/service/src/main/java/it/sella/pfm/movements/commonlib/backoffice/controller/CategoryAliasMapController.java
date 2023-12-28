package it.sella.pfm.movements.commonlib.backoffice.controller;

import io.swagger.annotations.Api;
import it.sella.pfm.movements.commonlib.backoffice.service.categoryaliasmap.CategoryAliasMapService;
import it.sella.pfm.movements.commonlib.entity.CategoryAliasMap;
import it.sella.pfm.movements.commonlib.to.CategoryAliasMapDTO;
import it.sella.pfm.movements.commonlib.utils.MappingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(description = "CURD API for Category Alias")
@RestController
@RequestMapping(value = "backoffice/category/alias")
public class CategoryAliasMapController extends BaseController<CategoryAliasMap, CategoryAliasMapDTO> {

    @Autowired
    CategoryAliasMapService categoryAliasMapService;

    @Autowired
    public CategoryAliasMapController(CategoryAliasMapService service) {
        super(service);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    MappingResponse<CategoryAliasMapDTO> getPage(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                                 @RequestParam(value = "limit", required = false, defaultValue = "200") Integer limit,
                                                 @RequestParam(value = "sortBy", required = false, defaultValue = "-category") String sortBy,
                                                 CategoryAliasMapDTO categoryAliasMapDTO) {
        return categoryAliasMapService.getPage(offset, limit, sortBy, categoryAliasMapDTO);
    }


}
