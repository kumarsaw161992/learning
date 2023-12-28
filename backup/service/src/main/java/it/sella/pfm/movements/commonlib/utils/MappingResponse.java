package it.sella.pfm.movements.commonlib.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class MappingResponse<T> {

    private String status;
    private String error;
    private T mapping;
    private List<T> mappingList;
    private Pagination pagination;
    private List<Sorting> sorting;
}
