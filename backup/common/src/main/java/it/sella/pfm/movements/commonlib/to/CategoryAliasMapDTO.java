package it.sella.pfm.movements.commonlib.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryAliasMapDTO extends BaseDTO {

    private static final long serialVersionUID = -4684600477474537736L;
    private String direction;
    private String category;
    private String subCategory;
    private String aliasCategory;
    private String aliasSubCategory;
    private String hypeCategory;
    private Boolean reverseDefault;
    private int typeId;

}
