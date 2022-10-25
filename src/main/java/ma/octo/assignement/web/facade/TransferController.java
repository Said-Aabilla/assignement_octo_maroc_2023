package ma.octo.assignement.web.facade;
import ma.octo.assignement.dto.request.TransferRequestDto;
import ma.octo.assignement.dto.response.TransferResponseDto;
import ma.octo.assignement.exceptions.AccountNotFoundException;
import ma.octo.assignement.exceptions.InsufficientBalanceException;
import ma.octo.assignement.exceptions.TransactionException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import static ma.octo.assignement.domain.util.ApiPaths.*;

@RestController
@RequestMapping(API+V1+TRANSFERS)
public interface TransferController {
    @GetMapping
    List<TransferResponseDto> getAllTransfers() ;

    @PostMapping
    TransferResponseDto createTransfer (@Valid @RequestBody TransferRequestDto transferRequestDto) throws AccountNotFoundException, TransactionException, InsufficientBalanceException;

}
