package issuetracker.controllers.project;

import issuetracker.assemblers.ProjectModelAssembler;
import issuetracker.domain.Project;
import issuetracker.persistence.ProjectRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/{account}/{project}")
public class ProjectController {

    private final ProjectRepository projectRepository;

    private final ProjectModelAssembler projectModelAssembler;

    public ProjectController(final ProjectRepository projectRepository, final ProjectModelAssembler projectModelAssembler) {
        this.projectRepository = projectRepository;
        this.projectModelAssembler = projectModelAssembler;
    }

    @GetMapping()
    public EntityModel<Project> one(final @PathVariable(name = "project") String projectName) {
        //TODO: Use {account} as well
        Project project = projectRepository.getByName(projectName).orElseThrow(ProjectNotFoundException::new);
        return projectModelAssembler.toModel(project);
    }

}
