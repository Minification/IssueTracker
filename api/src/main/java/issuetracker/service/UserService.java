package issuetracker.service;

import issuetracker.api.controllers.account.AccountNotFoundException;
import issuetracker.domain.model.Account;
import issuetracker.domain.dto.AccountView;
import issuetracker.domain.dto.CreateAccountRequest;
import issuetracker.domain.mappers.AccountEditMapper;
import issuetracker.domain.mappers.AccountViewMapper;
import issuetracker.repository.AccountRepository;
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
        final Account account = accountEditMapper.create(request);
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        final Account saved = accountRepository.save(account);
        return accountViewMapper.toAccountView(saved);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return accountRepository.findByEmail(s).orElseThrow(AccountNotFoundException::new);
    }

}
