package issuetracker.controllers;

import issuetracker.domain.Issue;
import issuetracker.persistence.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/{account}/{project}/issues")
public class IssueController {

    private final IssueRepository repository;

    @Autowired
    public IssueController(final IssueRepository issueRepository) {
        this.repository = issueRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Issue> showIssue(final @PathVariable String account,
                                           final @PathVariable String project,
                                           final @PathVariable Long id) {
        return repository.findById(id)
                .map(issue -> new ResponseEntity<>(issue, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
