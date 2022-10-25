package ma.octo.assignement.service.impl;

import ma.octo.assignement.domain.Account;
import ma.octo.assignement.domain.MoneyDeposit;
import ma.octo.assignement.dto.request.MoneyDepositRequestDto;
import ma.octo.assignement.dto.response.MoneyDepositResponseDto;
import ma.octo.assignement.exceptions.AccountNotFoundException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.mapper.facade.MoneyDepositMapper;
import ma.octo.assignement.repository.AccountRepository;
import ma.octo.assignement.repository.MoneyDepositRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MoneyDepositServiceImplTest {

    @InjectMocks
    private MoneyDepositServiceImpl moneyDepositService;

    @Mock
    private MoneyDepositMapper moneyDepositMapper;

    @Mock
    private MoneyDepositRepository moneyDepositRepository;


    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AuditService auditService;


    @Test
    void getAllMoneyDepositsShouldReturnMoneyDeposits() {

        MoneyDepositResponseDto moneyDepositResponseDto = MoneyDepositResponseDto.builder()
                .motif("testMotif")
                .amount(new BigDecimal(1000))
                .dateExecution(new Date())
                .fullNameTransmitter("said aabilla")
                .nrAccountBeneficiary("123456789")
                .build();

        when(moneyDepositMapper.toMoneyDepositResponseDtoList(any())).thenReturn(Stream.of( moneyDepositResponseDto)
                .collect(Collectors.toList()));

        List<MoneyDepositResponseDto> result = moneyDepositService.getAllDeposits();

        verify(moneyDepositRepository, times(1)).findAll();
        assertEquals(1, result.size());
        assertEquals("testMotif", result.get(0).getMotif());
        assertEquals(new BigDecimal(1000), result.get(0).getAmount());
        assertEquals("123456789", result.get(0).getNrAccountBeneficiary());
        assertEquals("said aabilla", result.get(0).getFullNameTransmitter());

    }

    @Test
    void getAllMoneyDepositsShouldReturnEmptyListWhenNoMoneyDepositWasFound() {


        when(moneyDepositRepository.findAll()).thenReturn(Collections.emptyList());
        when(moneyDepositMapper.toMoneyDepositResponseDtoList(anyList())).thenReturn(Collections.emptyList());

        List<MoneyDepositResponseDto> result = moneyDepositService.getAllDeposits();

        verify(moneyDepositRepository, times(1)).findAll();

        assertEquals(0, result.size());

    }

    @Nested
    class CreateMoneyDepositTests {

        public static final int MONTANT_MAXIMAL = 10000;
        public static final int MONTANT_MINIMAL = 10;


        @Test
        void shouldReturnMoneyDepositResponseDtoWhenMoneyDepositRequestDtoIsGivenAndValid() throws AccountNotFoundException, TransactionException {

            Account accountBenif = new Account();
            accountBenif.setNrAccount("010000A000001000");
            accountBenif.setSolde(new BigDecimal(50000));

            Date date = new Date();

            MoneyDepositRequestDto moneyDepositRequestDto = MoneyDepositRequestDto.builder()
                    .motif("testMotif")
                    .amount(new BigDecimal(1000))
                    .dateExecution(date)
                    .fullNameTransmitter("said aabilla")
                    .rib("RIB123")
                    .build();


            MoneyDepositResponseDto moneyDepositResponseDto = MoneyDepositResponseDto.builder()
                    .motif("testMotif")
                    .amount(new BigDecimal(1000))
                    .dateExecution(date)
                    .fullNameTransmitter("said aabilla")
                    .nrAccountBeneficiary("010000A000001000")
                    .build();


            MoneyDeposit deposit = MoneyDeposit.builder()
                    .id(1L)
                    .amount(new BigDecimal(120)).motif("testMotif1")
                    .dateExecution(date).accountBeneficiary(accountBenif).fullNameTransmitter("said aabilla")
                    .build();


            when(moneyDepositRepository.save(any())).thenReturn(deposit);
            when(accountRepository.findByRib(anyString())).thenReturn(Optional.of(accountBenif));
            when(moneyDepositMapper.toMoneyDepositResponseDto(deposit)).thenReturn(moneyDepositResponseDto);
            when(moneyDepositMapper.toMoneyDeposit(moneyDepositRequestDto, accountBenif)).thenReturn(deposit);

            MoneyDepositResponseDto result = moneyDepositService.createDeposit(moneyDepositRequestDto);

            verify(moneyDepositRepository, times(1)).save(any());
            assertEquals(moneyDepositResponseDto, result);

        }

        @Test
        void shouldThrowAccountNotFoundExceptionWhenAccountIsNotFoundByRib()  {
            String unregisteredRib = "rib1234";
            MoneyDepositRequestDto depositRequestDto = MoneyDepositRequestDto.builder().rib(unregisteredRib).build();

            when(accountRepository.findByRib(depositRequestDto.getRib())).thenReturn(Optional.empty());

            Executable result = () -> moneyDepositService.createDeposit(depositRequestDto);

            AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, result);

            assertEquals("account.not.found", exception.getMessage());
        }

        @Test
        void shouldThrowTransactionExceptionWhenMaxAmountIsReached()  {

            Account accountBenif = new Account();
            accountBenif.setRib("RIB123");

            MoneyDepositRequestDto depositRequestDto = MoneyDepositRequestDto.builder().amount(new BigDecimal(MONTANT_MAXIMAL+1)).build();

            when(accountRepository.findByRib(depositRequestDto.getRib())).thenReturn(Optional.of(accountBenif));

            Executable result = () -> moneyDepositService.createDeposit(depositRequestDto);

            TransactionException exception = assertThrows(TransactionException.class, result);

            assertEquals("Montant maximal de transfert dépassé", exception.getMessage());
        }

        @Test
        void shouldThrowTransactionExceptionWhenMinAmountIsReached()  {

            Account accountBenif = new Account();
            accountBenif.setRib("RIB123");

            MoneyDepositRequestDto depositRequestDto = MoneyDepositRequestDto.builder().amount(new BigDecimal(MONTANT_MINIMAL-1)).build();

            when(accountRepository.findByRib(depositRequestDto.getRib())).thenReturn(Optional.of(accountBenif));

            Executable result = () -> moneyDepositService.createDeposit(depositRequestDto);

            TransactionException exception = assertThrows(TransactionException.class, result);

            assertEquals("Montant minimal de transfert non atteint", exception.getMessage());
        }

        @Test
        void shouldThrowTransactionExceptionWhenAmountIsEmpty()  {

            Account accountBenif = new Account();
            accountBenif.setRib("RIB123");

            MoneyDepositRequestDto depositRequestDto = MoneyDepositRequestDto.builder().amount(new BigDecimal(0)).build();

            when(accountRepository.findByRib(depositRequestDto.getRib())).thenReturn(Optional.of(accountBenif));

            Executable result = () -> moneyDepositService.createDeposit(depositRequestDto);

            TransactionException exception = assertThrows(TransactionException.class, result);

            assertEquals("Montant vide", exception.getMessage());
        }

    }
}
