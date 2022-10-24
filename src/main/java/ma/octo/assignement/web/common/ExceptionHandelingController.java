package ma.octo.assignement.web.common;

import lombok.AllArgsConstructor;
import ma.octo.assignement.dto.response.ErrorResponseDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.utils.ApiMessageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

import static ma.octo.assignement.domain.util.ApiErrorCodes.*;

@ControllerAdvice
@AllArgsConstructor
public class ExceptionHandelingController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandelingController.class);

    private final ApiMessageSource apiMessageSource;



    @ExceptionHandler(SoldeDisponibleInsuffisantException.class)
    public ResponseEntity<ErrorResponseDto> handleSoldeDisponibleInsuffisantException(SoldeDisponibleInsuffisantException soldeDisponibleInsuffisantException) {

        logger.info("Handle SoldeDisponibleInsuffisantException:  {} ", soldeDisponibleInsuffisantException.getMessage());

        return ResponseEntity
                .status(HttpServletResponse.SC_BAD_REQUEST)
                .body(new ErrorResponseDto(SOLDE_DISPONIBLE_INSUFFISANT_EXCEPTION.getCode(), apiMessageSource.getMessage(SOLDE_DISPONIBLE_INSUFFISANT_EXCEPTION.getMessageKey())));
    }

    @ExceptionHandler(CompteNonExistantException.class)
    public ResponseEntity<ErrorResponseDto> handleCompteNonExistantException(CompteNonExistantException compteNonExistantException) {

        logger.info("Handle CompteNonExistantException:  {} ", compteNonExistantException.getMessage());

        return ResponseEntity
                .status(HttpServletResponse.SC_NOT_FOUND)
                .body(new ErrorResponseDto(COMPTE_NOT_FOUND_EXCEPTION.getCode(), apiMessageSource.getMessage(COMPTE_NOT_FOUND_EXCEPTION.getMessageKey())));

    }

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<ErrorResponseDto> handleTransactionException(TransactionException transactionException) {

        logger.info("Handle TransactionException:  {} ", transactionException.getMessage());

        return ResponseEntity
                .status(HttpServletResponse.SC_BAD_REQUEST)
                    .body(new ErrorResponseDto(TRANSACTION_EXCEPTION.getCode(), apiMessageSource.getMessage(transactionException.getMessage())));

    }
}
