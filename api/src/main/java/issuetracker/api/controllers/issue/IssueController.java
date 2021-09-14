package issuetracker.api.controllers.issue;

import issuetracker.api.assemblers.IssueModelAssembler;
import issuetracker.domain.model.Issue;
import issuetracker.domain.dto.CreateIssueRequest;
import issuetracker.repository.IssueRepository;
import issuetracker.configuration.security.JwtUtil;
import issuetracker.configuration.security.TokenParser;
import issuetracker.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/{account}/projects/{project}/issues")
@RequiredArgsConstructor
public class IssueController {

    private final IssueRepository repository;

    private final IssueModelAssembler issueModelAssembler;

    private final IssueService issueService;

    private final JwtUtil jwtUtil;

    @GetMapping()
    public CollectionModel<EntityModel<Issue>> all(final @PathVariable String account,
                                                   final @PathVariable String project) {
        //TODO: Use {account} and {project} as well
        final List<EntityModel<Issue>> entityModels = repository
                .findAll()
                .stream()
                .map(issueModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(entityModels, linkTo(methodOn(IssueController.class).all(account, project)).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Issue> one(final @PathVariable Long id) {
        //TODO: Use {account} and {project} as well
        final Issue issue = repository
                .findById(id)
                .orElseThrow(IssueNotFoundException::new);

        return issueModelAssembler.toModel(issue);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIssue(final @PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<?> createIssue(final @RequestBody @Valid CreateIssueRequest request,
                                         final @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                                         final @PathVariable("") String projectName) {
        final String token = TokenParser.extractTokenFromAuthorizationHeader(authorization);
        final long userId = Long.parseLong(jwtUtil.getUserId(token));
        return ResponseEntity.ok(issueService.create(request, userId, projectName));
    }

}
