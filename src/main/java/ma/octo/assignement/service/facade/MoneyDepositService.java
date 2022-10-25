package ma.octo.assignement.service.facade;


import ma.octo.assignement.dto.request.MoneyDepositRequestDto;
import ma.octo.assignement.dto.response.MoneyDepositResponseDto;
import ma.octo.assignement.exceptions.AccountNotFoundException;
import ma.octo.assignement.exceptions.TransactionException;

import java.util.List;

public interface MoneyDepositService {
    MoneyDepositResponseDto createDeposit (MoneyDepositRequestDto moneyDepositRequestDto) throws AccountNotFoundException, TransactionException;
    List<MoneyDepositResponseDto> getAllDeposits() ;

}
