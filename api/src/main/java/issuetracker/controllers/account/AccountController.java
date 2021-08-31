package issuetracker.controllers.account;

import issuetracker.assemblers.AccountModelAssembler;
import issuetracker.domain.Account;
import issuetracker.persistence.AccountRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/{account}")
public class AccountController {

    private final AccountRepository repository;

    private final AccountModelAssembler accountModelAssembler;

    public AccountController(final AccountRepository repository, final AccountModelAssembler accountModelAssembler) {
        this.repository = repository;
        this.accountModelAssembler = accountModelAssembler;
    }

    @GetMapping()
    public EntityModel<Account> one(final @PathVariable(name = "account") String accountName) {
        Account account = repository.getByName(accountName).orElseThrow(AccountNotFoundException::new);
        return accountModelAssembler.toModel(account);
    }

}
