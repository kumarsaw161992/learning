package it.sella.pfm.movements.commonlib.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Getter
@Setter
@Document(collection = "deq_instrument_type")
@NoArgsConstructor
public class InstrumentType implements Serializable {

    private static final long serialVersionUID = -7854049354686283453L;
    @Id
    private String operationType;
    private String operation;
    private String instrumentType;
}
