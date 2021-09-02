package issuetracker.services;

import issuetracker.controllers.account.AccountNotFoundException;
import issuetracker.domain.Account;
import issuetracker.dto.AccountView;
import issuetracker.dto.CreateAccountRequest;
import issuetracker.mappers.AccountEditMapper;
import issuetracker.mappers.AccountViewMapper;
import issuetracker.persistence.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    private final AccountEditMapper accountEditMapper;

    private final AccountViewMapper accountViewMapper;

    @Transactional
    public AccountView createAccount(final CreateAccountRequest request) {
        if (accountRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ValidationException("Email already in use!");
        }
        if (!request.getPassword().equals(request.getPasswordAgain())) {
            throw new ValidationException("Passwords don't match!");
        }
        Account account = accountEditMapper.create(request);
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account = accountRepository.save(account);
        return accountViewMapper.toAccountView(account);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return accountRepository.findByEmail(s).orElseThrow(AccountNotFoundException::new);
    }

}
