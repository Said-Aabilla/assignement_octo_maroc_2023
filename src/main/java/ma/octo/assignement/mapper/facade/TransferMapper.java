package ma.octo.assignement.mapper.facade;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Transfer;
import ma.octo.assignement.dto.request.TransferRequestDto;
import ma.octo.assignement.dto.response.TransferResponseDto;

import java.util.List;

public interface TransferMapper {

    public TransferResponseDto toTransferResponseDto(Transfer transfer);
    public Transfer toTransfer(TransferRequestDto transferRequestDto, Compte compteBeneficiaire,Compte compteEmetteur);

    public List<TransferResponseDto> toTransferResponseDtoList(List<Transfer> transfers);
}
