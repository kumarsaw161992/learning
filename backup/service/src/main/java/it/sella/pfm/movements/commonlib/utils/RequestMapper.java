package it.sella.pfm.movements.commonlib.utils;

import it.sella.pfm.movements.commonlib.exception.FabrickPFMMovementsCommonException;
import it.sella.pfm.movements.commonlib.exception.StatusCode;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class RequestMapper {

    public static <S, T> T copyPorps(S s, T t) {
        BeanUtils.copyProperties(s, t);
        return t;
    }

    public static void validateRequest(Integer offset, Integer limit) throws FabrickPFMMovementsCommonException {
        if (offset == null || offset < 0) {
            throw new FabrickPFMMovementsCommonException(StatusCode.INVALID_INPUT.getCode(), "offset must be a positive integer");
        }

        if (limit == null || limit <= 0) {
            throw new FabrickPFMMovementsCommonException(StatusCode.INVALID_INPUT.getCode(), "limit must be a positive integer");
        }
        else if (limit > 200) {
            throw new FabrickPFMMovementsCommonException(StatusCode.INVALID_INPUT.getCode(),
                    "limit must be less than 200, limit param value:" + limit);
        }
    }

    public static List<Sort.Order> getOrders(String sortBy) {
        List<Sort.Order> orderList = new ArrayList<>();
        Sort.Order order;
        String orders[] = sortBy.split(",");
        for (String odr : orders) {
            if (odr.charAt(0) == '-') {
                order = new Sort.Order(Sort.Direction.DESC, odr.substring(1));
            } else if (odr.charAt(0) == '+') {
                order = new Sort.Order(Sort.Direction.ASC, odr.substring(1));
            } else {
                order = new Sort.Order(Sort.Direction.ASC, odr);
            }
            orderList.add(order);
        }
        return orderList;
    }
}
