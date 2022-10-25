package ma.octo.assignement.web.common;

import lombok.AllArgsConstructor;
import ma.octo.assignement.dto.response.ErrorResponseDto;
import ma.octo.assignement.exceptions.AccountNotFoundException;
import ma.octo.assignement.exceptions.InsufficientBalanceException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.utils.ApiMessageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

import static ma.octo.assignement.domain.util.ApiErrorCodes.*;

@ControllerAdvice
@AllArgsConstructor
public class ExceptionHandlingController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlingController.class);

    private final ApiMessageSource apiMessageSource;



    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponseDto> handleInsufficientBalanceException(InsufficientBalanceException insufficientBalanceException) {

        logger.info("Handle InsufficientBalanceException:  {} ", insufficientBalanceException.getMessage());

        return ResponseEntity
                .status(HttpServletResponse.SC_BAD_REQUEST)
                .body(new ErrorResponseDto(INSUFFICIENT_BALANCE_EXCEPTION.getCode(), apiMessageSource.getMessage(INSUFFICIENT_BALANCE_EXCEPTION.getMessageKey())));
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleAccountNotFoundException(AccountNotFoundException accountNotFoundException) {

        logger.info("Handle AccountNotFoundException:  {} ", accountNotFoundException.getMessage());

        return ResponseEntity
                .status(HttpServletResponse.SC_NOT_FOUND)
                .body(new ErrorResponseDto(ACCOUNT_NOT_FOUND_EXCEPTION.getCode(), apiMessageSource.getMessage(ACCOUNT_NOT_FOUND_EXCEPTION.getMessageKey())));

    }

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<ErrorResponseDto> handleTransactionException(TransactionException transactionException) {

        logger.info("Handle TransactionException:  {} ", transactionException.getMessage());

        return ResponseEntity
                .status(HttpServletResponse.SC_BAD_REQUEST)
                    .body(new ErrorResponseDto(TRANSACTION_EXCEPTION.getCode(), apiMessageSource.getMessage(transactionException.getMessage())));

    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> HandlerOtherExceptions(MethodArgumentNotValidException ex, WebRequest request){

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->{
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
