package ma.octo.assignement.repository;

import ma.octo.assignement.domain.MoneyDeposit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoneyDepositRepository extends JpaRepository<MoneyDeposit, Long> {
}
