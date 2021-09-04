package issuetracker.controllers.project;

import issuetracker.assemblers.ProjectModelAssembler;
import issuetracker.domain.Project;
import issuetracker.dto.CreateProjectRequest;
import issuetracker.dto.ProjectView;
import issuetracker.persistence.ProjectRepository;
import issuetracker.security.JwtUtil;
import issuetracker.security.SplitBearer;
import issuetracker.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/{account}/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectRepository projectRepository;

    private final ProjectModelAssembler projectModelAssembler;

    private final ProjectService projectService;

    private final JwtUtil jwtUtil;

    @GetMapping("/{project}")
    public EntityModel<Project> one(final @PathVariable(name = "project") String projectName) {
        //TODO: Use {account} as well
        Project project = projectRepository.getByName(projectName).orElseThrow(ProjectNotFoundException::new);
        return projectModelAssembler.toModel(project);
    }

    @PostMapping()
    public ResponseEntity<ProjectView> createProject(
            final @RequestBody CreateProjectRequest request,
            final @RequestHeader("Authorization") String authorization) {
        String token = SplitBearer.extractTokenFromAuthorizationHeader(authorization);
        ProjectView projectView = projectService.create(request, Long.parseLong(jwtUtil.getUserId(token)));
        return ResponseEntity.ok(projectView);
    }

}
