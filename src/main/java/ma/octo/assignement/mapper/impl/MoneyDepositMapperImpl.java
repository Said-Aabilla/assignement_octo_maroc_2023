package ma.octo.assignement.mapper.impl;


import ma.octo.assignement.domain.Compte;
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
        moneyDepositResponseDto.setNrCompteBeneficiaire(deposit.getCompteBeneficiaire().getNrCompte());
        moneyDepositResponseDto.setMontant(deposit.getMontant());
        moneyDepositResponseDto.setDateExecution(deposit.getDateExecution());
        moneyDepositResponseDto.setMotif(deposit.getMotif());
        moneyDepositResponseDto.setFullNameEmetteur(deposit.getfullNameEmetteur());
        return moneyDepositResponseDto;
    }

    @Override
    public MoneyDeposit toMoneyDeposit(MoneyDepositRequestDto moneyDepositRequestDto, Compte compteBeneficiaire) {
        if (moneyDepositRequestDto == null) {
            return null;
        }
        MoneyDeposit deposit = new MoneyDeposit();
        deposit.setDateExecution(moneyDepositRequestDto.getDateExecution());
        deposit.setfullNameEmetteur(moneyDepositRequestDto.getFullNameEmetteur());
        deposit.setCompteBeneficiaire(compteBeneficiaire);
        deposit.setMontant(moneyDepositRequestDto.getMontant());
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
