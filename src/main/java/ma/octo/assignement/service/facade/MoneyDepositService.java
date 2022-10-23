package ma.octo.assignement.service.facade;


import ma.octo.assignement.dto.MoneyDepositRequestDto;
import ma.octo.assignement.dto.MoneyDepositResponseDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;

import java.util.List;

public interface MoneyDepositService {
    MoneyDepositResponseDto createDeposit (MoneyDepositRequestDto moneyDepositRequestDto) throws CompteNonExistantException, TransactionException;
    List<MoneyDepositResponseDto> getAllDeposits() ;

}
