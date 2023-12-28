package it.sella.pfm.movements.commonlib.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConfigParamDTO extends BaseDTO {

    private static final long serialVersionUID = 4781628927569942751L;
    private String name;
    private String value;
}
