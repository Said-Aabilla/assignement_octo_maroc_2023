package ma.octo.assignement.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.MoneyDeposit;
import ma.octo.assignement.dto.request.MoneyDepositRequestDto;
import ma.octo.assignement.dto.response.MoneyDepositResponseDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.mapper.facade.MoneyDepositMapper;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.MoneyDepositRepository;
import ma.octo.assignement.service.facade.AuditService;
import ma.octo.assignement.service.facade.MoneyDepositService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static ma.octo.assignement.domain.util.ApiErrorCodes.COMPTE_NOT_FOUND_EXCEPTION;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MoneyDepositServiceImpl implements MoneyDepositService {

    public static final int MONTANT_MAXIMAL = 10000;
    public static final int MONTANT_MINIMAL = 10;

    private final MoneyDepositMapper moneyDepositMapper;
    private final MoneyDepositRepository moneyDepositRepository;
    private final CompteRepository compteRepository;
    public final AuditService auditService;

    @Override
    public MoneyDepositResponseDto createDeposit(MoneyDepositRequestDto moneyDepositRequestDto) throws CompteNonExistantException, TransactionException {

        log.info("Calling findByRib from compteRepository to get compteBeneficiaire");
        Compte compteBeneficiaire = compteRepository.findByRib(moneyDepositRequestDto.getRib())
                .orElseThrow(() -> new CompteNonExistantException(COMPTE_NOT_FOUND_EXCEPTION.getMessageKey()));


        log.info("Calling validateDepositDetails from MoneyDepositServiceImpl");
        validateDepositDetails(moneyDepositRequestDto.getMotif(),moneyDepositRequestDto.getMontant().intValue());

        compteBeneficiaire
                .setSolde(new BigDecimal(compteBeneficiaire.getSolde().intValue() + moneyDepositRequestDto.getMontant().intValue()));
        compteRepository.save(compteBeneficiaire);

        log.info("Executing transfer");
        MoneyDeposit deposit = moneyDepositMapper.toMoneyDeposit(moneyDepositRequestDto, compteBeneficiaire);
        moneyDepositRepository.save(deposit);

        log.info("Saving auditDeposit");
        auditService.auditDeposit("Deposit par " + moneyDepositRequestDto.getFullNameEmetteur() + " vers " + moneyDepositRequestDto
                .getRib() + " d'un montant de " + moneyDepositRequestDto.getMontant()
                .toString());

        return moneyDepositMapper.toMoneyDepositResponseDto(deposit);
    }

    @Override
    public List<MoneyDepositResponseDto> getAllDeposits() {
        return moneyDepositMapper.toMoneyDepositResponseDtoList(moneyDepositRepository.findAll());
    }


    private void validateDepositDetails(String motif, int montant) throws TransactionException {

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

    }
}
