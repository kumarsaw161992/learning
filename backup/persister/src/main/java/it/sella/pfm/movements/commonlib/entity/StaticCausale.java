package it.sella.pfm.movements.commonlib.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document(collection = "deq_static_causale")
@NoArgsConstructor
public class StaticCausale extends BaseEntity {

    private static final long serialVersionUID = -2201218643144865490L;
    private String causale;

    private String causaleToBe;

    @CreatedDate
    private Date insertDate;

    private Boolean isDisabled;

    @LastModifiedDate
    private Date updateDate;

}
