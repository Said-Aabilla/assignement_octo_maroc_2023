package ma.octo.assignement.mapper.facade;

import ma.octo.assignement.domain.Account;
import ma.octo.assignement.domain.MoneyDeposit;
import ma.octo.assignement.dto.request.MoneyDepositRequestDto;
import ma.octo.assignement.dto.response.MoneyDepositResponseDto;

import java.util.List;

public interface MoneyDepositMapper {

    public MoneyDepositResponseDto toMoneyDepositResponseDto(MoneyDeposit deposit);
    public MoneyDeposit toMoneyDeposit(MoneyDepositRequestDto moneyDepositRequestDto, Account accountBeneficiaire);

    List<MoneyDepositResponseDto> toMoneyDepositResponseDtoList(List<MoneyDeposit> all);
}
