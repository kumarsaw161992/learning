package it.sella.pfm.movements.commonlib.to;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CausaleInternaDTO extends BaseDTO{

    private static final long serialVersionUID = 7084606942976762316L;
    private String causaleAbbreviation;
    private String causaleHost;
    private String causaleInternaCode;
    private String causaleInstrumentType;
    private String causaleStructured;
    private Boolean override;
}
