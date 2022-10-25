package ma.octo.assignement.mapper.impl;

import ma.octo.assignement.domain.Account;
import ma.octo.assignement.domain.MoneyDeposit;
import ma.octo.assignement.dto.response.MoneyDepositResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@ExtendWith(MockitoExtension.class)
public class MoneyDepositMapperImplTest {
    @InjectMocks
    private MoneyDepositMapperImpl moneyDepositMapper;

    @Test
    void toMoneyDepositResponseDtoShouldMapMoneyDepositToMoneyDepositDto() {
        Account account1 = new Account();
        account1.setNrAccount("010000A000001000");
        Date date = new Date();

        MoneyDeposit deposit = MoneyDeposit.builder()
                .amount(new BigDecimal(120))
                .motif("testMotif1").fullNameTransmitter("said aabilla")
                .accountBeneficiary(account1).dateExecution(date).build();

        MoneyDepositResponseDto result = moneyDepositMapper.toMoneyDepositResponseDto(deposit);

        assertEquals(new BigDecimal(120),result.getAmount());
        assertEquals("testMotif1",result.getMotif());
        assertEquals("said aabilla",result.getFullNameTransmitter());
        assertEquals(date,result.getDateExecution());
        assertEquals(account1.getNrAccount(),result.getNrAccountBeneficiary());

    }

    @Test
    void toMoneyDepositResponseDtoShouldReturnNullWhenMoneyDepositIsNull() {

        MoneyDepositResponseDto result = moneyDepositMapper.toMoneyDepositResponseDto(null);
        assertNull(result);

    }

    @Test
    void toMoneyDepositResponseDtoListShouldMapListOfMoneyDepositToListOfMoneyDepositResponseDto() {
        Account account1 = new Account();
        account1.setNrAccount("010000A000001000");
        Date date = new Date();

        MoneyDeposit deposit1 = MoneyDeposit.builder()
                .amount(new BigDecimal(120))
                .motif("testMotif1").fullNameTransmitter("said aabilla")
                .accountBeneficiary(account1).dateExecution(date).build();


        MoneyDeposit deposit2 = MoneyDeposit.builder()
                .amount(new BigDecimal(2000))
                .motif("testMotif2").fullNameTransmitter("Ahmed Elalaoui")
                .accountBeneficiary(account1).dateExecution(date).build();


        List<MoneyDepositResponseDto> result =
                moneyDepositMapper.toMoneyDepositResponseDtoList(Stream.of(deposit1,deposit2)
                        .collect(Collectors.toList()));



        assertEquals(new BigDecimal(120),result.get(0).getAmount());
        assertEquals("testMotif1",result.get(0).getMotif());
        assertEquals("said aabilla",result.get(0).getFullNameTransmitter());
        assertEquals(date,result.get(0).getDateExecution());
        assertEquals(account1.getNrAccount(),result.get(0).getNrAccountBeneficiary());

        assertEquals(new BigDecimal(2000),result.get(1).getAmount());
        assertEquals("testMotif2",result.get(1).getMotif());
        assertEquals("Ahmed Elalaoui",result.get(1).getFullNameTransmitter());
        assertEquals(date,result.get(1).getDateExecution());
        assertEquals(account1.getNrAccount(),result.get(1).getNrAccountBeneficiary());

    }

    @Test
    void toMoneyDepositResponseDtoListShouldReturnEmptyListWhenListOfMoneyDepositResponseDtoIsEmpty() {

        List<MoneyDepositResponseDto> result =
                moneyDepositMapper.toMoneyDepositResponseDtoList(Collections.emptyList());
        assertEquals(Collections.emptyList(),result);

    }
    @Test
    void toMoneyDepositResponseDtoListShouldReturnEmptyListWhenListOfMoneyDepositResponseDtoIsNull() {

        List<MoneyDepositResponseDto> result =
                moneyDepositMapper.toMoneyDepositResponseDtoList(null);
        assertEquals(Collections.emptyList(),result);

    }
}
