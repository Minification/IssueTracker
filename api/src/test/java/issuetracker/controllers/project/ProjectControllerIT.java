package issuetracker.controllers.project;

import issuetracker.controllers.helpers.ConfigureMvcTest;
import issuetracker.controllers.helpers.MvcTestHelper;
import issuetracker.dto.CreateProjectRequest;
import issuetracker.dto.ProjectView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ConfigureMvcTest
@SpringBootTest(properties = "logging.level.root=DEBUG")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProjectControllerIT {

    /*@Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private AccountRepository accountRepository;

    private Account account;

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
        this.account = accountRepository.save(account);
    }

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void testProjectCreation() throws Exception {
        // Arrange
        CreateProjectRequest createProjectRequest = new CreateProjectRequest();
        createProjectRequest.setName("Project name");
        final String authorizationValue = "Bearer " + jwtUtil.generateAccessToken(account);
        final ProjectView projectView = new ProjectView();
        projectView.setName("Project name");

        // Act
        ResultActions resultActions = mvc.perform(post("/example/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createProjectRequest))
                .header("Authorization", authorizationValue));

        // Assert
        resultActions.andExpect(status().isOk());
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals(projectView, asObject(ProjectView.class, contentAsString), "ProjectViews do not match!");
    } */

    @Test
    @DisplayName("Project creation successful")
    public void testProjectCreation(final MvcTestHelper testHelper) throws Exception {
        // Arrange
        CreateProjectRequest createProjectRequest = new CreateProjectRequest();
        createProjectRequest.setName("Project name");
        final String authorizationValue = "Bearer " + testHelper.getAccessToken();
        final ProjectView projectView = new ProjectView();
        projectView.setName("Project name");

        // Act
        ResultActions resultActions = testHelper.getMvc().perform(post("/example/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testHelper.asJsonString(createProjectRequest))
                .header(HttpHeaders.AUTHORIZATION, authorizationValue));

        // Assert
        resultActions.andExpect(status().isOk());
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals(projectView, testHelper.asObject(ProjectView.class, contentAsString), "ProjectViews do not match!");
    }

}