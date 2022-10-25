package ma.octo.assignement.web.impl;

import ma.octo.assignement.exceptions.AccountNotFoundException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.service.facade.MoneyDepositService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MoneyDepositControllerImplTest {
    @InjectMocks
    private MoneyDepositControllerImpl moneyDepositController;

    @Mock
    private MoneyDepositService moneyDepositService;

    @Test
    void getAllMoneyDepositsShouldCallGetMoneyDepositsFromService() {
        moneyDepositController.getAllDeposits();
        verify(moneyDepositService,times(1)).getAllDeposits();
    }

    @Test
    void createMoneyDepositShouldCallCreateMoneyDepositFromMoneyDepositService() throws  TransactionException, AccountNotFoundException {
        moneyDepositController.createDeposit(any());
        verify(moneyDepositService, times(1)).createDeposit(any());
    }
}
