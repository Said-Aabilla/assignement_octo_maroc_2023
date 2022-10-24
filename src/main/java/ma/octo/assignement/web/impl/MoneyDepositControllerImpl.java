package ma.octo.assignement.web.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.octo.assignement.dto.request.MoneyDepositRequestDto;
import ma.octo.assignement.dto.response.MoneyDepositResponseDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.service.facade.MoneyDepositService;
import ma.octo.assignement.web.facade.MoneyDepositController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static ma.octo.assignement.domain.util.ApiPaths.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(API+V1+DEPOSITS)
public class MoneyDepositControllerImpl implements MoneyDepositController {

    private final MoneyDepositService moneyDepositService;


    @Override
    public List<MoneyDepositResponseDto> getAllDeposits() {
        return  moneyDepositService.getAllDeposits();
    }

    @Override
    public MoneyDepositResponseDto createDeposit(MoneyDepositRequestDto moneyDepositRequestDto) throws CompteNonExistantException, TransactionException {
        return moneyDepositService.createDeposit(moneyDepositRequestDto);
    }
}
