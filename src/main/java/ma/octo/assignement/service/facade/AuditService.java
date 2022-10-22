package ma.octo.assignement.service.facade;

public interface AuditService {
    public void auditTransfer(String message);
    public void auditDeposit(String message) ;

    }
