package issuetracker.domain.mappers;

import issuetracker.domain.model.Account;
import issuetracker.domain.dto.AccountView;
import issuetracker.repository.AccountRepository;
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
