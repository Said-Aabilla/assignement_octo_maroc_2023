package ma.octo.assignement.mapper.impl;

import ma.octo.assignement.domain.Account;
import ma.octo.assignement.domain.Transfer;
import ma.octo.assignement.dto.request.TransferRequestDto;
import ma.octo.assignement.dto.response.TransferResponseDto;
import ma.octo.assignement.mapper.facade.TransferMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransferMapperImpl  implements TransferMapper {

    @Override
    public TransferResponseDto toTransferResponseDto(Transfer transfer) {
        if (transfer == null) {
            return null;
        }
        TransferResponseDto transferResponseDto = new TransferResponseDto();
        transferResponseDto.setNrAccountTransmitter(transfer.getAccountTransmitter().getNrAccount());
        transferResponseDto.setNrAccountBeneficiary(transfer.getAccountBeneficiary().getNrAccount());
        transferResponseDto.setAmount(transfer.getAmount());
        transferResponseDto.setDateExecution(transfer.getDateExecution());
        transferResponseDto.setMotif(transfer.getMotif());

        return transferResponseDto;

    }

    @Override
    public Transfer toTransfer(TransferRequestDto transferRequestDto, Account accountBeneficiaire, Account accountEmetteur) {
        if (transferRequestDto == null) {
            return null;
        }
        Transfer transfer = new Transfer();
        transfer.setDateExecution(transferRequestDto.getDateExecution());
        transfer.setAccountBeneficiary(accountBeneficiaire);
        transfer.setAccountTransmitter(accountEmetteur);
        transfer.setAmount(transferRequestDto.getAmount());
        transfer.setMotif(transferRequestDto.getMotif());
        return transfer;

    }

    @Override
    public List<TransferResponseDto> toTransferResponseDtoList(List<Transfer> transfers) {
        if (transfers == null || transfers.isEmpty()) {
            return Collections.emptyList();
        }
        return transfers.stream().map(this::toTransferResponseDto).collect(Collectors.toList());
    }
}
