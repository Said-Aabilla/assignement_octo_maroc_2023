package ma.octo.assignement.domain.util;


public enum ApiErrorCodes implements KeyValueError {

    MISSING_FIELD_ERROR(0, "field.not.found"),
    ACCOUNT_NOT_FOUND_EXCEPTION(1, "account.not.found"),
    TRANSACTION_EXCEPTION(2, "transaction.not.permitted"),
    INSUFFICIENT_BALANCE_EXCEPTION(3, "insufficient.balance"),

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
