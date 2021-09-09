package issuetracker.api.controllers.project;

import issuetracker.helpers.ConfigureMvcTest;
import issuetracker.helpers.MvcTestHelper;
import issuetracker.domain.dto.CreateProjectRequest;
import issuetracker.domain.dto.ProjectView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ConfigureMvcTest
@SpringBootTest(properties = "logging.level.root=DEBUG")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProjectControllerIT {

    @Test
    @DisplayName("Project creation successful")
    public void testProjectCreation(final MvcTestHelper testHelper) throws Exception {
        // Arrange
        final CreateProjectRequest createProjectRequest = new CreateProjectRequest();
        createProjectRequest.setName("Project name");
        final String authorizationValue = "Bearer " + testHelper.getAccessToken();
        final ProjectView projectView = new ProjectView();
        projectView.setName("Project name");

        // Act
        final ResultActions resultActions = testHelper.getMvc().perform(post("/example/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testHelper.asJsonString(createProjectRequest))
                .header(HttpHeaders.AUTHORIZATION, authorizationValue));

        // Assert
        resultActions.andExpect(status().isOk());
        final String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals(projectView, testHelper.asObject(ProjectView.class, contentAsString), "ProjectViews do not match!");
    }

}