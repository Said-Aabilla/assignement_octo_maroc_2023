package ma.octo.assignement.mapper.impl;


import ma.octo.assignement.domain.Account;
import ma.octo.assignement.domain.MoneyDeposit;
import ma.octo.assignement.dto.request.MoneyDepositRequestDto;
import ma.octo.assignement.dto.response.MoneyDepositResponseDto;
import ma.octo.assignement.mapper.facade.MoneyDepositMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MoneyDepositMapperImpl implements MoneyDepositMapper {


    @Override
    public MoneyDepositResponseDto toMoneyDepositResponseDto(MoneyDeposit deposit) {
        if (deposit == null) {
            return null;
        }
        MoneyDepositResponseDto moneyDepositResponseDto = new MoneyDepositResponseDto();
        moneyDepositResponseDto.setNrAccountBeneficiary(deposit.getAccountBeneficiary().getNrAccount());
        moneyDepositResponseDto.setAmount(deposit.getAmount());
        moneyDepositResponseDto.setDateExecution(deposit.getDateExecution());
        moneyDepositResponseDto.setMotif(deposit.getMotif());
        moneyDepositResponseDto.setFullNameTransmitter(deposit.getFullNameTransmitter());
        return moneyDepositResponseDto;
    }

    @Override
    public MoneyDeposit toMoneyDeposit(MoneyDepositRequestDto moneyDepositRequestDto, Account accountBeneficiary) {
        if (moneyDepositRequestDto == null) {
            return null;
        }
        MoneyDeposit deposit = new MoneyDeposit();
        deposit.setDateExecution(moneyDepositRequestDto.getDateExecution());
        deposit.setFullNameTransmitter(moneyDepositRequestDto.getFullNameTransmitter());
        deposit.setAccountBeneficiary(accountBeneficiary);
        deposit.setAmount(moneyDepositRequestDto.getAmount());
        deposit.setMotif(moneyDepositRequestDto.getMotif());
        return deposit;
    }

    @Override
    public List<MoneyDepositResponseDto> toMoneyDepositResponseDtoList(List<MoneyDeposit> deposits) {
        if (deposits == null || deposits.isEmpty()) {
            return Collections.emptyList();
        }
        return deposits.stream().map(this::toMoneyDepositResponseDto).collect(Collectors.toList());
    }
}
