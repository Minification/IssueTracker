package issuetracker.controllers.issue;

import issuetracker.assemblers.IssueModelAssembler;
import issuetracker.domain.Issue;
import issuetracker.persistence.IssueRepository;
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
public class IssueController {

    private final IssueRepository repository;

    private final IssueModelAssembler issueModelAssembler;

    @Autowired
    public IssueController(final IssueRepository issueRepository, final IssueModelAssembler issueModelAssembler) {
        this.repository = issueRepository;
        this.issueModelAssembler = issueModelAssembler;
    }

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

}
