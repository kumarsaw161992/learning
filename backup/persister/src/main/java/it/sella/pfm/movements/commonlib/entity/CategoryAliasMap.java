package it.sella.pfm.movements.commonlib.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "deq_category_alias")
@NoArgsConstructor
public class CategoryAliasMap extends BaseEntity{

    private static final long serialVersionUID = 4388586877494134628L;
    private String direction;
    private String category;
    private String subCategory;
    private String aliasCategory;
    private String aliasSubCategory;
    private String hypeCategory;
    private Boolean reverseDefault;
    private int typeId;

}
