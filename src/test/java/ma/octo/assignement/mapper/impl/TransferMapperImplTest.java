package ma.octo.assignement.mapper.impl;

import ma.octo.assignement.domain.Account;

import ma.octo.assignement.domain.Transfer;
import ma.octo.assignement.dto.response.TransferResponseDto;
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
public class TransferMapperImplTest {
    @InjectMocks
    private TransferMapperImpl TransferMapper;

    @Test
    void toTransferResponseDtoShouldMapTransferToTransferDto() {
        Account accountBenif = new Account();
        accountBenif.setNrAccount("010000A000001000");

        Account accountEmett = new Account();
        accountEmett.setNrAccount("020000A000002000");

        Date date = new Date();

        Transfer transfer = Transfer.builder()
                .amount(new BigDecimal(120)).motif("testMotif1")
                .accountBeneficiary(accountBenif).accountTransmitter(accountEmett)
                .dateExecution(date).build();

        TransferResponseDto result = TransferMapper.toTransferResponseDto(transfer);

        assertEquals(new BigDecimal(120), result.getAmount());
        assertEquals("testMotif1", result.getMotif());
        assertEquals("010000A000001000", result.getNrAccountBeneficiary());
        assertEquals("020000A000002000", result.getNrAccountTransmitter());
        assertEquals(date, result.getDateExecution());

    }

    @Test
    void toTransferResponseDtoShouldReturnNullWhenTransferIsNull() {

        TransferResponseDto result = TransferMapper.toTransferResponseDto(null);

        assertNull(result);

    }

    @Test
    void toTransferResponseDtoListShouldMapListOfTransferToListOfTransferResponseDto() {
        Account accountBenif = new Account();
        accountBenif.setNrAccount("010000A000001000");

        Account accountEmett = new Account();
        accountEmett.setNrAccount("020000A000002000");

        Date date = new Date();

        Transfer transfer1 = Transfer.builder()
                .amount(new BigDecimal(1000))
                .motif("testMotif1").accountTransmitter(accountEmett)
                .accountBeneficiary(accountBenif).dateExecution(date).build();


        Transfer transfer2 = Transfer.builder()
                .amount(new BigDecimal(2000))
                .motif("testMotif2").accountTransmitter(accountEmett)
                .accountBeneficiary(accountBenif).dateExecution(date).build();


        List<TransferResponseDto> result =
                TransferMapper.toTransferResponseDtoList(Stream.of(transfer1, transfer2)
                        .collect(Collectors.toList()));

        assertEquals(new BigDecimal(1000), result.get(0).getAmount());
        assertEquals("testMotif1", result.get(0).getMotif());
        assertEquals("010000A000001000", result.get(0).getNrAccountBeneficiary());
        assertEquals("020000A000002000", result.get(0).getNrAccountTransmitter());
        assertEquals(date, result.get(0).getDateExecution());

        assertEquals(new BigDecimal(2000), result.get(1).getAmount());
        assertEquals("testMotif2", result.get(1).getMotif());
        assertEquals("010000A000001000", result.get(1).getNrAccountBeneficiary());
        assertEquals("020000A000002000", result.get(1).getNrAccountTransmitter());
        assertEquals(date, result.get(1).getDateExecution());


    }

    @Test
    void toTransferResponseDtoListShouldReturnEmptyListWhenListOfTransferResponseDtoIsEmpty() {

        List<TransferResponseDto> result =
                TransferMapper.toTransferResponseDtoList(Collections.emptyList());


        assertEquals(Collections.emptyList(), result);

    }

    @Test
    void toTransferResponseDtoListShouldReturnEmptyListWhenListOfTransferResponseDtoIsNull() {

        List<TransferResponseDto> result =
                TransferMapper.toTransferResponseDtoList(null);


        assertEquals(Collections.emptyList(), result);

    }
}
