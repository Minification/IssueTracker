package issuetracker.api.controllers.authentication;

import issuetracker.helpers.ConfigureMvcTest;
import issuetracker.helpers.MvcTestHelper;
import issuetracker.domain.dto.LoginRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.ResultActions;

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
        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("example@example.com");
        loginRequest.setPassword("Example");

        // Act
        final ResultActions resultActions = testHelper.getMvc().perform(post("/authentication/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testHelper.asJsonString(loginRequest)));

        // Assert
        resultActions.andExpect(status().isOk());
    }

}