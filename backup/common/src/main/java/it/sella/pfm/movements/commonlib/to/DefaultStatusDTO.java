package it.sella.pfm.movements.commonlib.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DefaultStatusDTO extends BaseDTO{

    private static final long serialVersionUID = -4143232699016204143L;
    private String operationCode;
    private String operationType;
    private String status;
}
