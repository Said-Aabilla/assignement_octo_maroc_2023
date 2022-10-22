package ma.octo.assignement.repository;

import ma.octo.assignement.domain.AuditDeposit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditDepositRepository extends JpaRepository<AuditDeposit, Long> {
}