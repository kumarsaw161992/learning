package it.sella.pfm.movements.commonlib.exception;

import lombok.Getter;

@Getter
public class FabrickPFMMovementsCommonException extends Exception {

    private static final long serialVersionUID = 9001087134723335553L;

    private final String errCode;

    public FabrickPFMMovementsCommonException(StatusCode statusCode) {
        super(statusCode.getDesc());
        this.errCode = statusCode.getCode();
    }

    public FabrickPFMMovementsCommonException(String errCode, String errDesc) {
        super(errDesc);
        this.errCode = errCode;

    }
}
