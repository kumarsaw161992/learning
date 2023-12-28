package it.sella.pfm.movements.commonlib.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PageDetails<T> {

    public Pagination getPagination(Page<T> mappingPage) {
        if (mappingPage == null) {
            return null;
        }
        Pagination pagination = new Pagination();
        pagination.setLimit(mappingPage.getSize());
        pagination.setPageCount(mappingPage.getTotalPages());
        pagination.setResultCount(mappingPage.getTotalElements());
        return pagination;
    }

    public List<Sorting> getSorting(Page<T> mappingPage) {
        if (mappingPage == null) {
            return Collections.emptyList();
        }
        List<Sorting> sorting = new ArrayList<>();
        for (Sort.Order order : mappingPage.getSort()) {
            Sorting sort = new Sorting();
            sort.setFieldName(order.getProperty());
            sort.setDirection(order.getDirection());
            sorting.add(sort);
        }
        return sorting;
    }
}
