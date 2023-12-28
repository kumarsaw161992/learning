package it.sella.pfm.movements.commonlib.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DefaultCategoryDTO extends BaseDTO{

    private static final long serialVersionUID = 4156497475142255592L;
    private String operationType;

    private String direction;

    private String category;

    private String subCategory;

    private String operationCode;

    private Boolean recurrent;

    private Boolean override;

}
