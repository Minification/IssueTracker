package issuetracker.persistence;

import issuetracker.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository  extends JpaRepository<Account, Long> {

    Optional<Account> getByName(String name);

    Optional<Account> findByEmail(String email);

}
