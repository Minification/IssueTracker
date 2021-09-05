package issuetracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import issuetracker.domain.Account;
import issuetracker.persistence.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@Service
public class MvcTestHelper {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private AccountRepository accountRepository;

    private Account account;

    @Transactional
    public void reset() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        Account account = new Account();
        account.setId(1L);
        account.setEmail("example@example.com");
        account.setPassword("$2a$12$FhvXxZ2GN.TdnjeYDtX/SOQXGeeVfXFpo6KqN0sH4h.MtQDA.v0Oe"); //"Example"
        account.setEnabled(true);
        account.setName("Example User");
        account.setIssueIds(List.of());
        account.setProjectIds(List.of());
        this.account = accountRepository.save(account);
    }

    public MockMvc getMvc() {
        return mvc;
    }

    public String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Account getAccount() {
        return account;
    }
}
