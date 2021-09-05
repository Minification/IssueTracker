package issuetracker.services;

import issuetracker.controllers.account.AccountNotFoundException;
import issuetracker.controllers.project.ProjectNotFoundException;
import issuetracker.domain.Account;
import issuetracker.domain.Issue;
import issuetracker.domain.Project;
import issuetracker.dto.CreateIssueRequest;
import issuetracker.dto.IssueView;
import issuetracker.mappers.IssueEditMapper;
import issuetracker.mappers.IssueViewMapper;
import issuetracker.persistence.AccountRepository;
import issuetracker.persistence.IssueRepository;
import issuetracker.persistence.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;

    private final ProjectRepository projectRepository;

    private final AccountRepository accountRepository;

    private final IssueEditMapper issueEditMapper;

    private final IssueViewMapper issueViewMapper;

    @Transactional
    public IssueView create(final CreateIssueRequest createIssueRequest, final long owner, final String projectName) {
        Account ownerAccount = accountRepository.findById(owner).orElseThrow(AccountNotFoundException::new);
        Project project = projectRepository.getByName(projectName).orElseThrow(ProjectNotFoundException::new);

        Issue issue = issueEditMapper.create(createIssueRequest);
        issue.setOwnerId(owner);
        issue.setProjectId(project.getId());

        Issue save = issueRepository.save(issue);
        List<Long> issueIds = new ArrayList<>(ownerAccount.getIssueIds());
        issueIds.add(save.getId());
        ownerAccount.setIssueIds(issueIds);
        accountRepository.save(ownerAccount);

        return issueViewMapper.toIssueView(save);
    }

}
