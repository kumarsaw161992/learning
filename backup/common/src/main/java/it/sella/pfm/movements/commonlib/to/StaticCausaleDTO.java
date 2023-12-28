package it.sella.pfm.movements.commonlib.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StaticCausaleDTO extends BaseDTO {

    private static final long serialVersionUID = -7475949365065997412L;
    private String causale;
    private String causaleToBe;
    private Boolean isDisabled;


}
