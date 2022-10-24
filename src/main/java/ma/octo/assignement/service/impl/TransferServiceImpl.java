package ma.octo.assignement.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Transfer;
import ma.octo.assignement.dto.request.TransferRequestDto;
import ma.octo.assignement.dto.response.TransferResponseDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.mapper.facade.TransferMapper;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.TransferRepository;
import ma.octo.assignement.service.facade.AuditService;
import ma.octo.assignement.service.facade.TransferService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static ma.octo.assignement.domain.util.ApiErrorCodes.COMPTE_NOT_FOUND_EXCEPTION;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {


    public static final int MONTANT_MAXIMAL = 10000;
    public static final int MONTANT_MINIMAL = 10;

    private final TransferMapper transferMapper;
    private final TransferRepository transferRepository;
    private final CompteRepository compteRepository;
    public final AuditService auditService;


    @Override
    public List<TransferResponseDto> getAllTransfers() {

        return transferMapper.toTransferResponseDtoList(transferRepository.findAll());
    }

    @Override
    public TransferResponseDto createTransfer(TransferRequestDto transferRequestDto) throws CompteNonExistantException, TransactionException {
        log.info("Calling findByNrCompte from compteRepository to get compteEmetteur");
        Compte compteEmetteur = compteRepository.findByNrCompte(transferRequestDto.getNrCompteEmetteur())
                .orElseThrow(() -> new CompteNonExistantException(COMPTE_NOT_FOUND_EXCEPTION.getMessageKey()));

        log.info("Calling findByNrCompte from compteRepository to get compteBeneficiaire");
        Compte compteBeneficiaire = compteRepository.findByNrCompte(transferRequestDto.getNrCompteBeneficiaire())
                .orElseThrow(() -> new CompteNonExistantException(COMPTE_NOT_FOUND_EXCEPTION.getMessageKey()));

        log.info("Calling validateTransferDetails from TransferServiceImpl");
        validateTransferDetails(compteEmetteur, transferRequestDto.getMotif(),transferRequestDto.getMontant().intValue());

        log.info("Executing transfer");
        compteEmetteur.setSolde(compteEmetteur.getSolde().subtract(transferRequestDto.getMontant()));
        compteRepository.save(compteEmetteur);

        compteBeneficiaire
                .setSolde(new BigDecimal(compteBeneficiaire.getSolde().intValue() + transferRequestDto.getMontant().intValue()));
        compteRepository.save(compteBeneficiaire);

        log.info("Executing transfer");
        Transfer transfer = transferMapper.toTransfer(transferRequestDto,compteBeneficiaire,compteEmetteur);
        transferRepository.save(transfer);

        log.info("Saving auditTransfer");
        auditService.auditTransfer("Transfer depuis " + transferRequestDto.getNrCompteEmetteur() + " vers " + transferRequestDto
                .getNrCompteBeneficiaire() + " d'un montant de " + transferRequestDto.getMontant()
                .toString());

        return transferMapper.toTransferResponseDto(transfer);
    }

    private void validateTransferDetails(Compte compteEmetteur,
                                         String motif,
                                         int montant) throws TransactionException {
        if (montant == 0) {
            log.error("Montant vide");
            throw new TransactionException("Montant vide");
        } else if (montant < MONTANT_MINIMAL) {
            log.error("Montant minimal de transfert non atteint {}", MONTANT_MINIMAL);
            throw new TransactionException("Montant minimal de transfert non atteint");
        } else if (montant > MONTANT_MAXIMAL) {
            log.error("Montant minimal de transfert non atteint {}", MONTANT_MAXIMAL);
            throw new TransactionException("Montant maximal de transfert dépassé");
        }

        if (motif.isEmpty()) {
            log.error("Motif vide");
            throw new TransactionException("Motif vide");
        }

        if (compteEmetteur.getSolde().intValue() - montant < 0) {
            log.error("Solde insuffisant pour effectuer le transfert");
            throw new TransactionException("Solde insuffisant pour effectuer le transfert");
        }
    }

}
