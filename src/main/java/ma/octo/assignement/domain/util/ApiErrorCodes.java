package ma.octo.assignement.domain.util;


public enum ApiErrorCodes implements KeyValueError {

    MISSING_FIELD_ERROR(0, "field.not.found"),
    COMPTE_NOT_FOUND_EXCEPTION(1, "compte.not.found"),
    SOLDE_DISPONIBLE_INSUFFISANT_EXCEPTION(2, "solde.disponible.insuffisant"),

    ;

    private final Integer code;
    private final String messageKey;

    ApiErrorCodes(Integer code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessageKey() {
        return messageKey;
    }
}
