package it.sella.pfm.movements.commonlib.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Pagination implements Serializable{

    private static final long serialVersionUID = -5136960437099070270L;
    
    private int pageCount;
    private Long resultCount;
    private Integer offset;
    private Integer limit;

}
