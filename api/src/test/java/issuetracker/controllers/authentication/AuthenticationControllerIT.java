package issuetracker.controllers.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import issuetracker.domain.Account;
import issuetracker.dto.LoginRequest;
import issuetracker.persistence.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AuthenticationControllerIT {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    public void setup() {
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
        accountRepository.save(account);
    }

    @Test
    @DisplayName("User login successful")
    public void loginSuccess() throws Exception {
        //Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("example@example.com");
        loginRequest.setPassword("Example");

        //Act & assert
        mvc.perform(post("/authentication/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(loginRequest))
        ).andExpect(status().isOk());
    }

    public String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}