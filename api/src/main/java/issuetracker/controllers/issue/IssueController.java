package issuetracker.controllers.issue;

import issuetracker.assemblers.IssueModelAssembler;
import issuetracker.domain.Issue;
import issuetracker.dto.CreateIssueRequest;
import issuetracker.persistence.IssueRepository;
import issuetracker.security.JwtUtil;
import issuetracker.security.SplitBearer;
import issuetracker.services.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/{account}/{project}/issues")
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
        List<EntityModel<Issue>> entityModels = repository
                .findAll()
                .stream()
                .map(issueModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(entityModels, linkTo(methodOn(IssueController.class).all(account, project)).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Issue> one(final @PathVariable Long id) {
        //TODO: Use {account} and {project} as well
        Issue issue = repository
                .findById(id)
                .orElseThrow(IssueNotFoundException::new);

        return issueModelAssembler.toModel(issue);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIssue(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<?> createIssue(final @RequestBody CreateIssueRequest request,
                                         final @RequestHeader("Authorization") String authorization,
                                         final @PathVariable("") String projectName) {
        String token = SplitBearer.extractTokenFromAuthorizationHeader(authorization);
        long userId = Long.parseLong(jwtUtil.getUserId(token));
        return ResponseEntity.ok(issueService.create(request, userId, projectName));
    }

}
