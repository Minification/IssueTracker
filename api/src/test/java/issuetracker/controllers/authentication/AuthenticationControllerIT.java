package issuetracker.controllers.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import issuetracker.controllers.helpers.ConfigureMvcTest;
import issuetracker.controllers.helpers.MvcTestHelper;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ConfigureMvcTest
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AuthenticationControllerIT {

    @Test
    @DisplayName("User login successful")
    public void loginSuccess(final MvcTestHelper testHelper) throws Exception {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("example@example.com");
        loginRequest.setPassword("Example");

        // Act
        ResultActions resultActions = testHelper.getMvc().perform(post("/authentication/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testHelper.asJsonString(loginRequest)));

        // Assert
        resultActions.andExpect(status().isOk());
    }

}