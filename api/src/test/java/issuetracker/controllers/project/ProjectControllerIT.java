package issuetracker.controllers.project;

import issuetracker.controllers.ConfigureMvcTest;
import issuetracker.controllers.MvcTestHelper;
import issuetracker.domain.Account;
import issuetracker.dto.CreateProjectRequest;
import issuetracker.dto.LoginRequest;
import issuetracker.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ConfigureMvcTest
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProjectControllerIT {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void testProjectCreation(final MvcTestHelper testHelper) throws Exception {
        //Arrange
        CreateProjectRequest createProjectRequest = new CreateProjectRequest();
        createProjectRequest.setName("Project name");
        Account account = testHelper.getAccount();

        //Act & assert
        testHelper.getMvc().perform(post("/example/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testHelper.asJsonString(createProjectRequest))
                .header("Authorization", "Bearer " + jwtUtil.generateAccessToken(account))
        ).andExpect(status().isOk());
    }

}