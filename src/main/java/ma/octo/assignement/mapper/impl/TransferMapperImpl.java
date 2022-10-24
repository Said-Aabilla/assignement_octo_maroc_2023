package ma.octo.assignement.mapper.impl;

import ma.octo.assignement.domain.Compte;
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
        transferResponseDto.setNrCompteEmetteur(transfer.getCompteEmetteur().getNrCompte());
        transferResponseDto.setNrCompteBeneficiaire(transfer.getCompteBeneficiaire().getNrCompte());
        transferResponseDto.setMontant(transfer.getMontantTransfer());
        transferResponseDto.setDate(transfer.getDateExecution());
        transferResponseDto.setMotif(transfer.getMotifTransfer());

        return transferResponseDto;

    }

    @Override
    public Transfer toTransfer(TransferRequestDto transferRequestDto, Compte compteBeneficiaire, Compte compteEmetteur) {
        if (transferRequestDto == null) {
            return null;
        }
        Transfer transfer = new Transfer();
        transfer.setDateExecution(transferRequestDto.getDate());
        transfer.setCompteBeneficiaire(compteBeneficiaire);
        transfer.setCompteEmetteur(compteEmetteur);
        transfer.setMontantTransfer(transferRequestDto.getMontant());
        transfer.setMotifTransfer(transferRequestDto.getMotif());
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
