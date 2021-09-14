package issuetracker.api.controllers.project;

import issuetracker.api.assemblers.ProjectModelAssembler;
import issuetracker.domain.model.Project;
import issuetracker.domain.dto.CreateProjectRequest;
import issuetracker.domain.dto.ProjectView;
import issuetracker.repository.ProjectRepository;
import issuetracker.configuration.security.JwtUtil;
import issuetracker.configuration.security.TokenParser;
import issuetracker.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/{account}/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectModelAssembler projectModelAssembler;

    private final ProjectService projectService;

    private final JwtUtil jwtUtil;

    private final Logger logger;

    @GetMapping("/{project}")
    public ResponseEntity<ProjectView> one(
            final @PathVariable(name = "project") String projectName,
            final @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        final String token = TokenParser.extractTokenFromAuthorizationHeader(authorization);
        ProjectView projectView = projectService.findByName(projectName, Long.parseLong(token));
        return ResponseEntity.ok(projectView);
    }

    @PostMapping()
    public ResponseEntity<ProjectView> createProject(
            final @RequestBody @Valid CreateProjectRequest request,
            final @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        final String token = TokenParser.extractTokenFromAuthorizationHeader(authorization);
        final ProjectView projectView = projectService.create(request, Long.parseLong(jwtUtil.getUserId(token)));
        return ResponseEntity.ok(projectView);
    }

    @DeleteMapping("/{project}")
    public ResponseEntity<?> createProject(
            final @PathVariable(name = "project") String projectName,
            final @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        final String token = TokenParser.extractTokenFromAuthorizationHeader(authorization);
        projectService.delete(projectName, Long.parseLong(jwtUtil.getUserId(token)));
        return ResponseEntity.ok().build();
    }

}
