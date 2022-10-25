package ma.octo.assignement.web.impl;

import ma.octo.assignement.exceptions.AccountNotFoundException;
import ma.octo.assignement.exceptions.InsufficientBalanceException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.service.facade.TransferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TransferControllerImplTest {
    @InjectMocks
    private TransferControllerImpl transferController;

    @Mock
    private TransferService transferService;

    @Test
    void getAllTransfersShouldCallGetAllTransfersFromTransferService() {
        transferController.getAllTransfers();
        verify(transferService,times(1)).getAllTransfers();
    }

    @Test
    void createTransferShouldCallCreateTransferFromTransferService() throws TransactionException, AccountNotFoundException, InsufficientBalanceException {
        transferController.createTransfer(any());
        verify(transferService, times(1)).createTransfer(any());
    }
}
