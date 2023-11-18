package com.learning.learningproject.emp.util;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public static <S, T> T copyPorps(S s, T t) {
        BeanUtils.copyProperties(s, t);
        return t;
    }
}
