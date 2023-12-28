package it.sella.pfm.movements.commonlib.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "deq_config_param")
@NoArgsConstructor
public class ConfigParam extends BaseEntity {

    private static final long serialVersionUID = -4764460722622288776L;
    private String name;

    private String value;
}
