package it.sella.pfm.movements.commonlib.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class InstrumentTypeDTO implements Serializable {

    private static final long serialVersionUID = -2363327754478188163L;
    protected String operationType;
    protected String operation;
    protected String instrumentType;
}
