package ma.octo.assignement.repository;

import ma.octo.assignement.domain.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompteRepository extends JpaRepository<Compte, Long> {
  Optional<Compte> findByNrCompte(String nrCompte);
  Optional<Compte> findByRib(String rib);
}
