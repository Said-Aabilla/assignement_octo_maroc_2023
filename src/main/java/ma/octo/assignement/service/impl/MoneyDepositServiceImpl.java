package ma.octo.assignement.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import ma.octo.assignement.service.facade.MoneyDepositService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static ma.octo.assignement.domain.util.ApiErrorCodes.ACCOUNT_NOT_FOUND_EXCEPTION;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MoneyDepositServiceImpl implements MoneyDepositService {

    public static final int MONTANT_MAXIMAL = 10000;
    public static final int MONTANT_MINIMAL = 10;

    private final MoneyDepositMapper moneyDepositMapper;
    private final MoneyDepositRepository moneyDepositRepository;
    private final AccountRepository accountRepository;
    public final AuditService auditService;

    @Override
    public MoneyDepositResponseDto createDeposit(MoneyDepositRequestDto moneyDepositRequestDto) throws AccountNotFoundException, TransactionException {

        log.info("Calling findByRib from accountRepository to get accountBeneficiary");
        Account accountBeneficiary = accountRepository.findByRib(moneyDepositRequestDto.getRib())
                .orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND_EXCEPTION.getMessageKey()));


        log.info("Calling validateDepositDetails from MoneyDepositServiceImpl");
        validateDepositDetails(moneyDepositRequestDto.getMotif(),moneyDepositRequestDto.getAmount().intValue());

        accountBeneficiary
                .setSolde(new BigDecimal(accountBeneficiary.getSolde().intValue() + moneyDepositRequestDto.getAmount().intValue()));
        accountRepository.save(accountBeneficiary);

        log.info("Executing transfer");
        MoneyDeposit deposit = moneyDepositMapper.toMoneyDeposit(moneyDepositRequestDto, accountBeneficiary);
        moneyDepositRepository.save(deposit);

        log.info("Saving auditDeposit");
        auditService.auditDeposit("Deposit par " + moneyDepositRequestDto.getFullNameTransmitter() + " vers " + moneyDepositRequestDto
                .getRib() + " d'un montant de " + moneyDepositRequestDto.getAmount()
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
