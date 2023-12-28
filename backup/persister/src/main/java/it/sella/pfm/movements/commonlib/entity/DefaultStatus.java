package it.sella.pfm.movements.commonlib.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "deq_default_status")
public class DefaultStatus extends BaseEntity {

    private static final long serialVersionUID = -3090278693123564804L;
    private String operationCode;

    private String operationType;

    private String status;
}
