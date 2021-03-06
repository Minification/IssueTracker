package issuetracker.service;

import issuetracker.api.controllers.account.AccountNotFoundException;
import issuetracker.api.controllers.project.ProjectNotFoundException;
import issuetracker.domain.model.Account;
import issuetracker.domain.model.Project;
import issuetracker.domain.dto.CreateProjectRequest;
import issuetracker.domain.dto.ProjectView;
import issuetracker.domain.mappers.ProjectEditMapper;
import issuetracker.domain.mappers.ProjectViewMapper;
import issuetracker.repository.AccountRepository;
import issuetracker.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final ProjectEditMapper projectEditMapper;

    private final ProjectViewMapper projectViewMapper;

    private final AccountRepository accountRepository;

    private final Logger logger;

    @Transactional(readOnly = true)
    public ProjectView findByName(final String projectName, final long owner) {
        final Account account = accountRepository.findById(owner).orElseThrow(AccountNotFoundException::new);
        final Project project = projectRepository.getByNameAndOwner(projectName, owner).orElseThrow(ProjectNotFoundException::new);
        return projectViewMapper.projectToView(project);
    }

    @Transactional
    public ProjectView create(final CreateProjectRequest request, final long owner) {
        final Project project = projectEditMapper.create(request);
        final Account account = accountRepository.findById(owner).orElseThrow(AccountNotFoundException::new);

        final var projectIds = new ArrayList<>(account.getProjectIds());
        project.setOwnerId(owner);
        final Project save = projectRepository.save(project);

        projectIds.add(save.getId());
        account.setProjectIds(projectIds);
        accountRepository.save(account);

        return projectViewMapper.projectToView(save);
    }

    @Transactional
    public void delete(final String projectName, final long owner) {
        final Account account = accountRepository.findById(owner).orElseThrow(AccountNotFoundException::new);
        final Project project = projectRepository.getByNameAndOwner(projectName, owner).orElseThrow(ProjectNotFoundException::new);
        projectRepository.delete(project);
        //TODO: Delete all issues for this project
    }
}
