package ma.octo.assignement.web.facade;

import ma.octo.assignement.dto.MoneyDepositRequestDto;
import ma.octo.assignement.dto.MoneyDepositResponseDto;
import ma.octo.assignement.dto.TransferResponseDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static ma.octo.assignement.domain.util.ApiPaths.*;

@RestController
@RequestMapping(API+V1+DEPOSITS)
public interface MoneyDepositController {

    @GetMapping
    List<MoneyDepositResponseDto> getAllDeposits() ;
    @PostMapping
    MoneyDepositResponseDto createDeposit (@Valid @RequestBody MoneyDepositRequestDto moneyDepositRequestDto) throws CompteNonExistantException, TransactionException;

}
