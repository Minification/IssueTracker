package issuetracker.services;

import issuetracker.controllers.account.AccountNotFoundException;
import issuetracker.domain.Account;
import issuetracker.domain.Project;
import issuetracker.dto.CreateProjectRequest;
import issuetracker.dto.ProjectView;
import issuetracker.mappers.ProjectEditMapper;
import issuetracker.mappers.ProjectViewMapper;
import issuetracker.persistence.AccountRepository;
import issuetracker.persistence.ProjectRepository;
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

    @Transactional
    public ProjectView create(final CreateProjectRequest request, final long owner) {
        logger.debug("Enter create");
        Project project = projectEditMapper.create(request);
        Account account = accountRepository.findById(owner).orElseThrow(AccountNotFoundException::new);

        var projectIds = new ArrayList<>(account.getProjectIds());
        project.setOwnerId(owner);
        Project save = projectRepository.save(project);

        logger.debug("Saved project is: {}", save);

        projectIds.add(save.getId());
        account.setProjectIds(projectIds);
        accountRepository.save(account);

        return projectViewMapper.projectToView(save);
    }

}
