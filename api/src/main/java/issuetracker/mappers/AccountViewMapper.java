package issuetracker.mappers;

import issuetracker.domain.Account;
import issuetracker.dto.AccountView;
import issuetracker.persistence.AccountRepository;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public abstract class AccountViewMapper {

    @Autowired
    private AccountRepository accountRepository;

    public abstract AccountView toAccountView(final Account account);
    public abstract List<AccountView> toAccountView(final List<Account> accounts);

}
