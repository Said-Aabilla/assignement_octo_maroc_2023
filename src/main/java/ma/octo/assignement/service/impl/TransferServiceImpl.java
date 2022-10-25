package ma.octo.assignement.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.octo.assignement.domain.Account;
import ma.octo.assignement.domain.Transfer;
import ma.octo.assignement.dto.request.TransferRequestDto;
import ma.octo.assignement.dto.response.TransferResponseDto;
import ma.octo.assignement.exceptions.AccountNotFoundException;
import ma.octo.assignement.exceptions.InsufficientBalanceException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.mapper.facade.TransferMapper;
import ma.octo.assignement.repository.AccountRepository;
import ma.octo.assignement.repository.TransferRepository;
import ma.octo.assignement.service.facade.AuditService;
import ma.octo.assignement.service.facade.TransferService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static ma.octo.assignement.domain.util.ApiErrorCodes.ACCOUNT_NOT_FOUND_EXCEPTION;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {


    public static final int MAX_AMOUNT = 10000;
    public static final int MIN_AMOUNT = 10;

    private final TransferMapper transferMapper;
    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;
    public final AuditService auditService;


    @Override
    public List<TransferResponseDto> getAllTransfers() {

        return transferMapper.toTransferResponseDtoList(transferRepository.findAll());
    }

    @Override
    public TransferResponseDto createTransfer(TransferRequestDto transferRequestDto) throws AccountNotFoundException, TransactionException, InsufficientBalanceException {
        log.info("Calling findByNrAccount from accountRepository to get accountTransmitter");
        Account accountTransmitter = accountRepository.findByNrAccount(transferRequestDto.getNrAccountTransmitter())
                .orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND_EXCEPTION.getMessageKey()));

        log.info("Calling findByNrAccount from accountRepository to get accountBeneficiary");
        Account accountBeneficiaire = accountRepository.findByNrAccount(transferRequestDto.getNrAccountBeneficiary())
                .orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND_EXCEPTION.getMessageKey()));

        log.info("Calling validateTransferDetails from TransferServiceImpl");
        validateTransferDetails(accountTransmitter, transferRequestDto.getMotif(),transferRequestDto.getAmount().intValue());

        log.info("Executing transfer");
        accountTransmitter.setSolde(accountTransmitter.getSolde().subtract(transferRequestDto.getAmount()));
        accountRepository.save(accountTransmitter);

        accountBeneficiaire
                .setSolde(new BigDecimal(accountBeneficiaire.getSolde().intValue() + transferRequestDto.getAmount().intValue()));
        accountRepository.save(accountBeneficiaire);

        log.info("Executing transfer");
        Transfer transfer = transferMapper.toTransfer(transferRequestDto, accountBeneficiaire, accountTransmitter);
        transferRepository.save(transfer);

        log.info("Saving auditTransfer");
        auditService.auditTransfer("Transfer depuis " + transferRequestDto.getNrAccountTransmitter() + " vers " + transferRequestDto
                .getNrAccountBeneficiary() + " d'un montant de " + transferRequestDto.getAmount()
                .toString());

        return transferMapper.toTransferResponseDto(transfer);
    }

    private void validateTransferDetails(Account accountTransmitter,
                                         String motif,
                                         int montant) throws TransactionException, InsufficientBalanceException {
        if (montant == 0) {
            log.error("Montant vide");
            throw new TransactionException("Montant vide");
        } else if (montant < MIN_AMOUNT) {
            log.error("Montant minimal de transfert non atteint {}", MIN_AMOUNT);
            throw new TransactionException("Montant minimal de transfert non atteint");
        } else if (montant > MAX_AMOUNT) {
            log.error("Montant minimal de transfert non atteint {}", MAX_AMOUNT);
            throw new TransactionException("Montant maximal de transfert dépassé");
        }

        if (motif.isEmpty()) {
            log.error("Motif vide");
            throw new TransactionException("Motif vide");
        }

        if (accountTransmitter.getSolde().intValue() - montant < 0) {
            log.error("Solde insuffisant pour effectuer le transfert");
            throw new InsufficientBalanceException("Solde insuffisant pour effectuer le transfert");
        }
    }

}
