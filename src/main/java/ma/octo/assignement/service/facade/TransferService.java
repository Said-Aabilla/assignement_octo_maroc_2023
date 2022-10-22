package ma.octo.assignement.service.facade;

import ma.octo.assignement.dto.TransferRequestDto;
import ma.octo.assignement.dto.TransferResponseDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;

import java.util.List;

public interface TransferService {
    List<TransferResponseDto> getAllTransfers();
    TransferResponseDto createTransfer (TransferRequestDto transferRequestDto) throws CompteNonExistantException, TransactionException;
}
