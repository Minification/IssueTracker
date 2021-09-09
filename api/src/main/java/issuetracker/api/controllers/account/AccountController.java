package issuetracker.api.controllers.account;

import issuetracker.api.assemblers.AccountModelAssembler;
import issuetracker.domain.model.Account;
import issuetracker.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/{account}")
@RequiredArgsConstructor
public class AccountController {

    private final AccountRepository repository;

    private final AccountModelAssembler accountModelAssembler;

    @GetMapping()
    public EntityModel<Account> one(final @PathVariable(name = "account") String accountName) {
        Account account = repository.getByName(accountName).orElseThrow(AccountNotFoundException::new);
        return accountModelAssembler.toModel(account);
    }

}
