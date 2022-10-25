package ma.octo.assignement.service.impl;

import ma.octo.assignement.domain.Account;
import ma.octo.assignement.domain.Transfer;
import ma.octo.assignement.dto.request.TransferRequestDto;
import ma.octo.assignement.dto.response.TransferResponseDto;
import ma.octo.assignement.exceptions.AccountNotFoundException;
import ma.octo.assignement.exceptions.InsufficientBalanceException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.mapper.facade.TransferMapper;
import ma.octo.assignement.repository.AccountRepository;
import ma.octo.assignement.repository.TransferRepository;
import ma.octo.assignement.service.facade.AuditService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransferServiceImplTest {

    @InjectMocks
    private TransferServiceImpl transferService;

    @Mock
    private TransferMapper transferMapper;

    @Mock
    private TransferRepository transferRepository;


    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AuditService auditService;


    @Test
    void getTransfersShouldReturnTransfers() {

        Date date = new Date();
        TransferResponseDto transferResponseDto = TransferResponseDto.builder()
                .motif("testMotif")
                .amount(new BigDecimal(1000))
                .dateExecution(date)
                .nrAccountTransmitter("987654321")
                .nrAccountBeneficiary("123456789")
                .build();

        when(transferMapper.toTransferResponseDtoList(any())).thenReturn(Stream.of( transferResponseDto)
                .collect(Collectors.toList()));

        List<TransferResponseDto> result = transferService.getAllTransfers();

        verify(transferRepository, times(1)).findAll();
        assertEquals(1, result.size());
        assertEquals("testMotif", result.get(0).getMotif());
        assertEquals(new BigDecimal(1000), result.get(0).getAmount());
        assertEquals("123456789", result.get(0).getNrAccountBeneficiary());
        assertEquals("987654321", result.get(0).getNrAccountTransmitter());
        assertEquals(date, result.get(0).getDateExecution());

    }

    @Test
    void getAllTransfersShouldReturnEmptyListWhenNoTransferWasFound() {


        when(transferRepository.findAll()).thenReturn(Collections.emptyList());
        when(transferMapper.toTransferResponseDtoList(anyList())).thenReturn(Collections.emptyList());

        List<TransferResponseDto> result = transferService.getAllTransfers();

        verify(transferRepository, times(1)).findAll();

        assertEquals(0, result.size());

    }

    @Nested
    class CreateTransferTests {

        public static final int MAX_AMOUNT = 10000;
        public static final int MIN_AMOUNT = 10;


        @Test
        void shouldReturnTransferResponseDtoWhenTransferRequestDtoIsGivenAndValid() throws TransactionException, InsufficientBalanceException, AccountNotFoundException {

            Account accountBeneficiary = new Account();
            accountBeneficiary.setNrAccount("010000A000001000");
            accountBeneficiary.setSolde(new BigDecimal(50000));

            Account accountTransmitter = new Account();
            accountTransmitter.setNrAccount("010000A000001000");
            accountTransmitter.setSolde(new BigDecimal(50000));

            Date date = new Date();

            TransferRequestDto transferRequestDto = TransferRequestDto.builder()
                    .motif("testMotif")
                    .amount(new BigDecimal(1000))
                    .dateExecution(date)
                    .nrAccountBeneficiary("010000A000001000")
                    .nrAccountTransmitter("010000A000001000")
                    .build();


            TransferResponseDto transferResponseDto = TransferResponseDto.builder()
                    .motif("testMotif")
                    .amount(new BigDecimal(1000))
                    .dateExecution(date)
                    .nrAccountBeneficiary("010000A000001000")
                    .nrAccountTransmitter("010000A000001000")
                    .build();


            Transfer transfer = Transfer.builder()
                    .id(1L)
                    .amount(new BigDecimal(1000))
                    .motif("testMotif")
                    .dateExecution(date)
                    .accountBeneficiary(accountBeneficiary)
                    .accountTransmitter(accountTransmitter)
                    .build();


            when(transferRepository.save(any())).thenReturn(transfer);
            when(accountRepository.findByNrAccount("010000A000001000")).thenReturn(Optional.of(accountBeneficiary));
            when(accountRepository.findByNrAccount("010000A000001000")).thenReturn(Optional.of(accountTransmitter));
            when(transferMapper.toTransferResponseDto(transfer)).thenReturn(transferResponseDto);
            when(transferMapper.toTransfer(transferRequestDto, accountBeneficiary,accountTransmitter)).thenReturn(transfer);

            TransferResponseDto result = transferService.createTransfer(transferRequestDto);

            verify(transferRepository, times(1)).save(any());
            assertEquals(transferResponseDto, result);

        }

        @Test
        void shouldThrowAccountNotFoundExceptionWhenAccountIsNotFound()  {
            String unregisteredNumber = "123456789";
            TransferRequestDto transferRequestDto = TransferRequestDto.builder()
                    .nrAccountBeneficiary("010000A000001000")
                    .nrAccountTransmitter(unregisteredNumber)
                    .motif("test")
                    .amount(new BigDecimal(MIN_AMOUNT-1)).build();

            when(accountRepository.findByNrAccount(transferRequestDto.getNrAccountTransmitter())).thenReturn(Optional.empty());

            Executable result = () -> transferService.createTransfer(transferRequestDto);

            AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, result);

            assertEquals("account.not.found", exception.getMessage());
        }

        @Test
        void shouldThrowTransactionExceptionWhenMaxAmountIsReached()  {

            Account accountBeneficiary = new Account();
            accountBeneficiary.setNrAccount("010000A000001000");

            Account accountTransmitter = new Account();
            accountTransmitter.setNrAccount("010000A000001000");



            TransferRequestDto transferRequestDto = TransferRequestDto.builder()
                    .nrAccountBeneficiary("010000A000001000")
                    .nrAccountTransmitter("010000A000001000")
                    .motif("test")
                    .amount(new BigDecimal(MAX_AMOUNT+1)).build();
            when(accountRepository.findByNrAccount(transferRequestDto.getNrAccountTransmitter())).thenReturn(Optional.of(accountTransmitter));
            when(accountRepository.findByNrAccount(transferRequestDto.getNrAccountBeneficiary())).thenReturn(Optional.of(accountBeneficiary));

            Executable result = () -> transferService.createTransfer(transferRequestDto);

            TransactionException exception = assertThrows(TransactionException.class, result);

            assertEquals("Montant maximal de transfert dépassé", exception.getMessage());
        }

        @Test
        void shouldThrowTransactionExceptionWhenMinAmountIsReached()  {


            Account accountBeneficiary = new Account();
            accountBeneficiary.setNrAccount("010000A000001000");

            Account accountTransmitter = new Account();
            accountTransmitter.setNrAccount("010000A000001000");



            TransferRequestDto transferRequestDto = TransferRequestDto.builder()
                    .nrAccountBeneficiary("010000A000001000")
                    .nrAccountTransmitter("010000A000001000")
                    .motif("test")
                    .amount(new BigDecimal(MIN_AMOUNT-1)).build();

            when(accountRepository.findByNrAccount(transferRequestDto.getNrAccountTransmitter())).thenReturn(Optional.of(accountTransmitter));
            when(accountRepository.findByNrAccount(transferRequestDto.getNrAccountBeneficiary())).thenReturn(Optional.of(accountBeneficiary));

            Executable result = () -> transferService.createTransfer(transferRequestDto);

            TransactionException exception = assertThrows(TransactionException.class, result);

            assertEquals("Montant minimal de transfert non atteint", exception.getMessage());
        }

        @Test
        void shouldThrowTransactionExceptionWhenAmountIsEmpty()  {

            Account accountBeneficiary = new Account();
            accountBeneficiary.setNrAccount("010000A000001000");

            Account accountTransmitter = new Account();
            accountTransmitter.setNrAccount("010000A000001000");



            TransferRequestDto transferRequestDto = TransferRequestDto.builder()
                    .nrAccountBeneficiary("010000A000001000")
                    .nrAccountTransmitter("010000A000001000")
                    .amount(new BigDecimal(0))
                    .motif("test")
                    .build();

            when(accountRepository.findByNrAccount(transferRequestDto.getNrAccountTransmitter())).thenReturn(Optional.of(accountTransmitter));
            when(accountRepository.findByNrAccount(transferRequestDto.getNrAccountBeneficiary())).thenReturn(Optional.of(accountBeneficiary));

            Executable result = () -> transferService.createTransfer(transferRequestDto);

            TransactionException exception = assertThrows(TransactionException.class, result);

            assertEquals("Montant vide", exception.getMessage());
        }


        @Test
        void shouldThrowInsufficientBalanceExceptionWhenAmountIsBiggerThanBalance()  {

            Account accountBeneficiary = new Account();
            accountBeneficiary.setNrAccount("010000A000001000");
            accountBeneficiary.setSolde(new BigDecimal(1000));


            Account accountTransmitter = new Account();
            accountTransmitter.setNrAccount("010000A000001000");
            accountTransmitter.setSolde(new BigDecimal(1000));



            TransferRequestDto transferRequestDto = TransferRequestDto.builder()
                    .nrAccountBeneficiary("010000A000001000")
                    .nrAccountTransmitter("010000A000001000")
                    .amount(new BigDecimal(2000))
                    .motif("test")
                    .build();

            when(accountRepository.findByNrAccount(transferRequestDto.getNrAccountTransmitter())).thenReturn(Optional.of(accountTransmitter));
            when(accountRepository.findByNrAccount(transferRequestDto.getNrAccountBeneficiary())).thenReturn(Optional.of(accountBeneficiary));

            Executable result = () -> transferService.createTransfer(transferRequestDto);

            InsufficientBalanceException exception = assertThrows(InsufficientBalanceException.class, result);

            assertEquals("Solde insuffisant pour effectuer le transfert", exception.getMessage());
        }

    }
}
