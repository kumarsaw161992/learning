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
@NoArgsConstructor
@Document(collection = "deq_causale_instrument_type")
public class CausaleInterna extends BaseEntity {

    private static final long serialVersionUID = 4104212900774916707L;
    private String causaleAbbreviation;

    private String causaleHost;

    private String causaleInternaCode;

    private String causaleInstrumentType;

    private String causaleStructured;

    private Boolean override;

    @CreatedDate
    private Date insertDate;

    @LastModifiedDate
    private Date updateDate;

}
