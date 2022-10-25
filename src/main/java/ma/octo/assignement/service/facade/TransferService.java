package ma.octo.assignement.service.facade;

import ma.octo.assignement.dto.request.TransferRequestDto;
import ma.octo.assignement.dto.response.TransferResponseDto;
import ma.octo.assignement.exceptions.AccountNotFoundException;
import ma.octo.assignement.exceptions.InsufficientBalanceException;
import ma.octo.assignement.exceptions.TransactionException;

import java.util.List;

public interface TransferService {
    List<TransferResponseDto> getAllTransfers();
    TransferResponseDto createTransfer (TransferRequestDto transferRequestDto) throws AccountNotFoundException, TransactionException, InsufficientBalanceException;
}
