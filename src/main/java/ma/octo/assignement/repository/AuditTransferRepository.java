package ma.octo.assignement.repository;

import ma.octo.assignement.domain.AuditTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditTransferRepository extends JpaRepository<AuditTransfer, Long> {
}
