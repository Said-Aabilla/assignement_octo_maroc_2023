package ma.octo.assignement.repository;

import ma.octo.assignement.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
  Optional<Account> findByNrAccount(String nrAccount);
  Optional<Account> findByRib(String rib);
}
