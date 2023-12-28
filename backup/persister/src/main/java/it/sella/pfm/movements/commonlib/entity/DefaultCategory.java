package it.sella.pfm.movements.commonlib.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "deq_default_category")
@NoArgsConstructor
public class DefaultCategory extends BaseEntity {

    private static final long serialVersionUID = -4756788346291142203L;
    private String operationType;

    private String direction;

    private String category;

    private String subCategory;

    private String operationCode;

    private Boolean recurrent;

    private Boolean override;

}
