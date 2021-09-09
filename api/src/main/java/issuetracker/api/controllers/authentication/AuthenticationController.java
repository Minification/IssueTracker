package issuetracker.api.controllers.authentication;

import issuetracker.domain.model.Account;
import issuetracker.domain.dto.AccountView;
import issuetracker.domain.dto.CreateAccountRequest;
import issuetracker.domain.dto.LoginRequest;
import issuetracker.domain.mappers.AccountViewMapper;
import issuetracker.configuration.security.JwtUtil;
import issuetracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final AccountViewMapper accountViewMapper;

    private final JwtUtil jwtUtil;

    private final UserService userService;

    private final Logger logger;

    @PostMapping("login")
    public ResponseEntity<AccountView> login(final @RequestBody @Valid LoginRequest loginRequest) {
        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            final Account account = (Account) authentication.getPrincipal();

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, jwtUtil.generateAccessToken(account))
                    .body(accountViewMapper.toAccountView(account));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("register")
    public AccountView register(final @RequestBody @Valid CreateAccountRequest request) {
        return userService.createAccount(request);
    }

}
