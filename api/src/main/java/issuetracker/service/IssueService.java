package issuetracker.service;

import issuetracker.api.controllers.account.AccountNotFoundException;
import issuetracker.api.controllers.project.ProjectNotFoundException;
import issuetracker.domain.model.Account;
import issuetracker.domain.model.Issue;
import issuetracker.domain.model.Project;
import issuetracker.domain.dto.CreateIssueRequest;
import issuetracker.domain.dto.IssueView;
import issuetracker.domain.mappers.IssueEditMapper;
import issuetracker.domain.mappers.IssueViewMapper;
import issuetracker.repository.AccountRepository;
import issuetracker.repository.IssueRepository;
import issuetracker.repository.ProjectRepository;
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
        final Account ownerAccount = accountRepository.findById(owner).orElseThrow(AccountNotFoundException::new);
        final Project project = projectRepository.getByName(projectName).orElseThrow(ProjectNotFoundException::new);

        final Issue issue = issueEditMapper.create(createIssueRequest);
        issue.setOwnerId(owner);
        issue.setProjectId(project.getId());

        final Issue save = issueRepository.save(issue);
        final List<Long> issueIds = new ArrayList<>(ownerAccount.getIssueIds());
        issueIds.add(save.getId());
        ownerAccount.setIssueIds(issueIds);
        accountRepository.save(ownerAccount);

        return issueViewMapper.toIssueView(save);
    }

}
