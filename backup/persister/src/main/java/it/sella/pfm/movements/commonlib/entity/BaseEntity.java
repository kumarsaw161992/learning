package it.sella.pfm.movements.commonlib.entity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public abstract class BaseEntity {

    @org.springframework.data.annotation.Id
    private Long id;
}
