package ma.octo.assignement.repository;

import ma.octo.assignement.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
