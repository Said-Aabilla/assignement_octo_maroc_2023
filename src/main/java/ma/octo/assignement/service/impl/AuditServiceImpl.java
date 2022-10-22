package ma.octo.assignement.service.impl;

import ma.octo.assignement.domain.AuditDeposit;
import ma.octo.assignement.domain.AuditTransfer;
import ma.octo.assignement.domain.util.EventType;
import ma.octo.assignement.repository.AuditDepositRepository;
import ma.octo.assignement.repository.AuditTransferRepository;
import ma.octo.assignement.service.facade.AuditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AuditServiceImpl  implements AuditService {

    Logger LOGGER = LoggerFactory.getLogger(AuditService.class);

    @Autowired
    private AuditTransferRepository auditTransferRepository;
    private AuditDepositRepository auditDepositRepository;

    @Override
    public void auditTransfer(String message) {

        LOGGER.info("Audit de l'événement {}", EventType.TRANSFER);

        AuditTransfer audit = new AuditTransfer();
        audit.setEventType(EventType.TRANSFER);
        audit.setMessage(message);
        auditTransferRepository.save(audit);
    }


    @Override
    public void auditDeposit(String message) {

        LOGGER.info("Audit de l'événement {}", EventType.DEPOSIT);
        AuditDeposit audit = new AuditDeposit();
        audit.setEventType(EventType.DEPOSIT);
        audit.setMessage(message);
        auditDepositRepository.save(audit);
    }
}
